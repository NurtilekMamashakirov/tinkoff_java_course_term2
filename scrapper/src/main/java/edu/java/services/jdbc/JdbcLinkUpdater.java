package edu.java.services.jdbc;

import edu.java.clients.bot.BotClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.dto.GitHubEventResponse;
import edu.java.clients.github.handler.EventHandler;
import edu.java.clients.stackOverflow.StackOverflowClient;
import edu.java.clients.stackOverflow.dto.StackOverflowResponse;
import edu.java.dto.models.Chat;
import edu.java.dto.models.Link;
import edu.java.dto.request.LinkUpdate;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcTgChatRepository;
import edu.java.services.LinkUpdater;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class JdbcLinkUpdater implements LinkUpdater {

    private JdbcLinkRepository linksDao;
    private JdbcTgChatRepository tgChatDao;
    private GitHubClient gitHubClient;
    private StackOverflowClient stackOverflowClient;
    private BotClient botClient;
    private List<EventHandler> eventHandlers;
    private static final Integer NUM_OF_LAST_N_LINKS = 20;

    @Override
    public int update() {
        int numOfUpdates = 0;
        List<Link> links = linksDao.getLastNLinks(NUM_OF_LAST_N_LINKS);
        for (Link link : links) {
            if (gitHubClient.isValidated(link.getUrl())) {
//                GitHubResponse gitHubResponse = gitHubClient.fetch(link.getUrl().getPath());
//                if (gitHubResponse.updatedAt().isAfter(link.getUpdatedAt())) {
//                    linksDao.updateUpdatedTime(link.getUrl().toString(), gitHubResponse.updatedAt());
//                    List<Chat> chatsOfLink = tgChatDao.getChatsByLink(link.getId());
//                    List<Integer> chatsIds = chatsOfLink
//                        .stream()
//                        .map(chat -> chat.getId().intValue())
//                        .toList();
//                    LinkUpdate linkUpdate = new LinkUpdate(
//                        link.getId().intValue(),
//                        link.getUrl(),
//                        DESCRIPTION,
//                        chatsIds
//                    );
//                    botClient.fetch(linkUpdate);
//                    numOfUpdates++;
//                }
//                linksDao.updateCheckedTime(link.getUrl().toString());
                log.info(link.getUrl().toString());
                numOfUpdates += handleGithubLink(link);
            }
            if (stackOverflowClient.isValidated(link.getUrl())) {
//                StackOverflowResponse stackOverflowResponse = stackOverflowClient.fetch(link.getUrl().getPath());
//                if (stackOverflowResponse.items().get(0).lastActivityDate().isAfter(link.getUpdatedAt())) {
//                    linksDao.updateUpdatedTime(
//                        link.getUrl().toString(),
//                        stackOverflowResponse.items().get(0).lastActivityDate()
//                    );
//                    List<Chat> chatsOfLink = tgChatDao.getChatsByLink(link.getId());
//                    List<Integer> chatsIds = chatsOfLink
//                        .stream()
//                        .map(chat -> chat.getId().intValue())
//                        .toList();
//                    LinkUpdate linkUpdate = new LinkUpdate(
//                        link.getId().intValue(),
//                        link.getUrl(),
//                        DESCRIPTION,
//                        chatsIds
//                    );
//                    botClient.fetch(linkUpdate);
//                    numOfUpdates++;
//                }
//                linksDao.updateCheckedTime(link.getUrl().toString());
                numOfUpdates += handleStackOverflowLink(link);
            }
        }
        return numOfUpdates;
    }

    private int handleGithubLink(Link link) {
        linksDao.updateCheckedTime(link.getUrl().toString());
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
        List<Chat> chatsOfLink = tgChatDao.getChatsByLink(link.getId());
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
        botClient.fetch(linkUpdate);
        linksDao.updateUpdatedTime(link.getUrl().toString(), events.getLast().updatedAt());
        return 1;
    }

    private int handleStackOverflowLink(Link link) {
        StackOverflowResponse stackOverflowResponse =
            stackOverflowClient.fetch(getPathOfStackOverflowLink(link.getUrl().toString()));
        if (stackOverflowResponse.items().getFirst().lastActivityDate().isAfter(link.getUpdatedAt())) {
            linksDao.updateUpdatedTime(
                link.getUrl().toString(),
                stackOverflowResponse.items().getFirst().lastActivityDate()
            );
            List<Chat> chatsOfLink = tgChatDao.getChatsByLink(link.getId());
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
            botClient.fetch(linkUpdate);
            linksDao.updateCheckedTime(link.getUrl().toString());
            return 1;
        }
        linksDao.updateCheckedTime(link.getUrl().toString());
        return 0;
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
