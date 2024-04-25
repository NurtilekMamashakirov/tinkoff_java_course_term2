package edu.java.services.jpa;

import edu.java.clients.bot.BotClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.dto.GitHubEventResponse;
import edu.java.clients.github.handler.EventHandler;
import edu.java.clients.stackOverflow.StackOverflowClient;
import edu.java.clients.stackOverflow.dto.StackOverflowResponse;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.entity.ChatEntity;
import edu.java.dto.entity.LinkEntity;
import edu.java.dto.request.LinkUpdate;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.services.LinkUpdater;
import edu.java.services.kafka.QueueProducer;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaLinkUpdater implements LinkUpdater {

    private JpaLinkRepository linkRepository;
    private GitHubClient gitHubClient;
    private StackOverflowClient stackOverflowClient;
    private BotClient botClient;
    private List<EventHandler> eventHandlers;
    private ApplicationConfig applicationConfig;
    private QueueProducer queueProducer;
    private final static int NUM_OF_LAST_N_LINKS = 20;

    @Override
    public int update() {
        int numOfUpdates = 0;
        List<LinkEntity> links = getLastNLinks();
        for (LinkEntity link : links) {
            if (gitHubClient.isValidated(link.getUrl())) {
                numOfUpdates += handleGithubLink(link);
            }
            if (stackOverflowClient.isValidated(link.getUrl())) {
                numOfUpdates += handleStackOverflowLink(link);
            }
        }
        return numOfUpdates;
    }

    private int handleStackOverflowLink(LinkEntity link) {
        StackOverflowResponse stackOverflowResponse =
            stackOverflowClient.fetch(getPathOfStackOverflowLink(link.getUrl().getPath()));
        if (stackOverflowResponse.items().getFirst().lastActivityDate().isAfter(link.getUpdatedAt())) {
            link.setUpdatedAt(stackOverflowResponse.items().getFirst().lastActivityDate());
            List<ChatEntity> chatsOfLink = link.getChats().stream().toList();
            List<Integer> chatsIds = chatsOfLink
                .stream()
                .map(chat -> chat.getId().intValue())
                .toList();
            LinkUpdate linkUpdate = new LinkUpdate(
                link.getId().intValue(),
                link.getUrl(),
                "Обновление по данной ссылке",
                chatsIds
            );
            if (applicationConfig.useQueue()) {
                queueProducer.send(linkUpdate);
            } else {
                botClient.fetch(linkUpdate);
            }
            link.setCheckedAt(OffsetDateTime.now());
            linkRepository.save(link);
            return 1;
        }
        link.setCheckedAt(OffsetDateTime.now());
        linkRepository.save(link);
        return 0;
    }

    private int handleGithubLink(LinkEntity link) {
        link.setCheckedAt(OffsetDateTime.now());
        linkRepository.save(link);
        List<GitHubEventResponse> events = gitHubClient.fetchEvents(link.getUrl().getPath());
        events = events.stream()
            .filter(event -> event.updatedAt().isAfter(link.getUpdatedAt()))
            .sorted(Comparator.comparing(GitHubEventResponse::updatedAt))
            .toList();
        if (events.isEmpty()) {
            return 0;
        }
        StringBuilder description = new StringBuilder();
        for (GitHubEventResponse event : events) {
            for (EventHandler eventHandler : eventHandlers) {
                if (eventHandler.supports(event)) {
                    description.append(eventHandler.handle(event));
                }
            }
        }
        List<ChatEntity> chatsOfLink = link.getChats().stream().toList();
        List<Integer> chatsIds = chatsOfLink
            .stream()
            .map(chat -> chat.getId().intValue())
            .toList();
        LinkUpdate linkUpdate = new LinkUpdate(
            link.getId().intValue(),
            link.getUrl(),
            description.toString(),
            chatsIds
        );
        if (applicationConfig.useQueue()) {
            queueProducer.send(linkUpdate);
        } else {
            botClient.fetch(linkUpdate);
        }
        link.setUpdatedAt(events.getLast().updatedAt());
        linkRepository.save(link);
        return 1;
    }

    private List<LinkEntity> getLastNLinks() {
        List<LinkEntity> linkEntities = linkRepository.findAll();
        return linkEntities
            .stream()
            .sorted(Comparator.comparing(LinkEntity::getCheckedAt))
            .limit(JpaLinkUpdater.NUM_OF_LAST_N_LINKS)
            .toList();
    }

    private String getPathOfStackOverflowLink(String url) {
        final Pattern urlPattern = Pattern.compile("$https://(ru.|)stackoverflow.com/questions/(.*)/(.*)^");
        Matcher matcher = urlPattern.matcher(url);
        if (matcher.matches()) {
            String questionId = matcher.group(2);
            return "/" + questionId + "?site=stackoverflow";
        }
        return null;
    }
}
