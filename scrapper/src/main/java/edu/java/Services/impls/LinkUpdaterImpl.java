package edu.java.Services.impls;

import edu.java.Services.LinkUpdater;
import edu.java.clients.BotClient;
import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import edu.java.dto.models.Chat;
import edu.java.dto.models.Link;
import edu.java.dto.request.LinkUpdate;
import edu.java.dto.response.GitHubResponse;
import edu.java.dto.response.StackOverflowResponse;
import edu.java.repository.jdbc.JdbcLinksDao;
import edu.java.repository.jdbc.JdbcTgChatDao;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LinkUpdaterImpl implements LinkUpdater {

    private JdbcLinksDao linksDao;
    private JdbcTgChatDao tgChatDao;
    private GitHubClient gitHubClient;
    private StackOverflowClient stackOverflowClient;
    private BotClient botClient;
    private static final Integer NUM_OF_LAST_N_LINKS = 20;
    private static final String GITHUB_API_HOST = "api.github.com";
    private static final String STACK_OVERFLOW_API_HOST = "api.stackexchange.com";
    private static final String DESCRIPTION = "This link was updated";

    @Override
    public int update() {
        List<Link> links = linksDao.getLastNLinks(NUM_OF_LAST_N_LINKS);
        for (Link link : links) {
            if (link.getLink().getHost().equalsIgnoreCase(GITHUB_API_HOST)) {
                GitHubResponse gitHubResponse = gitHubClient.fetch(link.getLink().getPath());
                if (gitHubResponse.updatedAt().isAfter(link.getUpdatedAt())) {
                    linksDao.updateUpdatedTime(link.getLink().toString(), gitHubResponse.updatedAt());
                    List<Chat> chatsOfLink = tgChatDao.getChatsByLink(link.getId());
                    List<Integer> chatsIds = chatsOfLink
                        .stream()
                        .map(chat -> chat.getId().intValue())
                        .toList();
                    LinkUpdate linkUpdate = new LinkUpdate(
                        link.getId().intValue(),
                        link.getLink(),
                        DESCRIPTION,
                        chatsIds
                    );
                    botClient.fetch(linkUpdate);
                }
                linksDao.updateCheckedTime(link.getLink().toString());
            }

            if (link.getLink().getHost().equalsIgnoreCase(STACK_OVERFLOW_API_HOST)) {
                StackOverflowResponse stackOverflowResponse = stackOverflowClient.fetch(link.getLink().getPath());
                if (stackOverflowResponse.lastActivityDate().isAfter(link.getUpdatedAt())) {
                    linksDao.updateUpdatedTime(link.getLink().toString(), stackOverflowResponse.lastActivityDate());
                    List<Chat> chatsOfLink = tgChatDao.getChatsByLink(link.getId());
                    List<Integer> chatsIds = chatsOfLink
                        .stream()
                        .map(chat -> chat.getId().intValue())
                        .toList();
                    LinkUpdate linkUpdate = new LinkUpdate(
                        link.getId().intValue(),
                        link.getLink(),
                        DESCRIPTION,
                        chatsIds
                    );
                    botClient.fetch(linkUpdate);
                }
                linksDao.updateCheckedTime(link.getLink().toString());
            }
        }
        return 0;
    }
}