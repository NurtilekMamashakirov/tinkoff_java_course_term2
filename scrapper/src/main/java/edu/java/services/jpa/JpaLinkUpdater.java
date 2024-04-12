package edu.java.services.jpa;

import edu.java.clients.BotClient;
import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import edu.java.dto.entity.ChatEntity;
import edu.java.dto.entity.LinkEntity;
import edu.java.dto.request.LinkUpdate;
import edu.java.dto.response.GitHubResponse;
import edu.java.dto.response.StackOverflowResponse;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.services.LinkUpdater;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaLinkUpdater implements LinkUpdater {

    private JpaLinkRepository linkRepository;
    private GitHubClient gitHubClient;
    private StackOverflowClient stackOverflowClient;
    private BotClient botClient;
    private static final String GITHUB_HOST = "github.com";
    private static final String STACK_OVERFLOW_API_HOST = "stackoverflow.com";
    private static final String DESCRIPTION = "This link was updated";
    private final static int NUM_OF_LAST_N_LINKS = 20;

    @Override
    public int update() {
        int numOfUpdates = 0;
        List<LinkEntity> links = getLastNLinks();
        for (LinkEntity link : links) {
            if (link.getUrl().getHost().equalsIgnoreCase(GITHUB_HOST)) {
                GitHubResponse gitHubResponse = gitHubClient.fetch(link.getUrl().getPath());
                if (gitHubResponse.updatedAt().isAfter(link.getUpdatedAt())) {
                    link.setUpdatedAt(gitHubResponse.updatedAt());
                    linkRepository.save(link);
                    List<ChatEntity> chatsOfLink = link.getChats().stream().toList();
                    List<Integer> chatsIds = chatsOfLink
                        .stream()
                        .map(chat -> chat.getId().intValue())
                        .toList();
                    LinkUpdate linkUpdate = new LinkUpdate(
                        link.getId().intValue(),
                        link.getUrl(),
                        DESCRIPTION,
                        chatsIds
                    );
                    botClient.fetch(linkUpdate);
                    numOfUpdates++;
                }
                link.setCheckedAt(OffsetDateTime.now());
                linkRepository.save(link);
            }

            if (link.getUrl().getHost().equalsIgnoreCase(STACK_OVERFLOW_API_HOST)) {
                StackOverflowResponse stackOverflowResponse = stackOverflowClient.fetch(link.getUrl().getPath());
                if (stackOverflowResponse.items().get(0).lastActivityDate().isAfter(link.getUpdatedAt())) {
                    link.setUpdatedAt(stackOverflowResponse.items().get(0).lastActivityDate());
                    linkRepository.save(link);
                    List<ChatEntity> chatsOfLink = link.getChats().stream().toList();
                    List<Integer> chatsIds = chatsOfLink
                        .stream()
                        .map(chat -> chat.getId().intValue())
                        .toList();
                    LinkUpdate linkUpdate = new LinkUpdate(
                        link.getId().intValue(),
                        link.getUrl(),
                        DESCRIPTION,
                        chatsIds
                    );
                    botClient.fetch(linkUpdate);
                    numOfUpdates++;
                }
                link.setCheckedAt(OffsetDateTime.now());
            }
        }
        return numOfUpdates;
    }

    private List<LinkEntity> getLastNLinks() {
        List<LinkEntity> linkEntities = linkRepository.findAll();
        return linkEntities
            .stream()
            .sorted(Comparator.comparing(LinkEntity::getCheckedAt))
            .limit(JpaLinkUpdater.NUM_OF_LAST_N_LINKS)
            .toList();
    }
}
