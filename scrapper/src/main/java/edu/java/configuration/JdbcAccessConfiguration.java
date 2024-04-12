package edu.java.configuration;

import edu.java.clients.BotClient;
import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import edu.java.repository.jdbc.JdbcLinksDao;
import edu.java.repository.jdbc.JdbcTgChatDao;
import edu.java.services.LinkService;
import edu.java.services.LinkUpdater;
import edu.java.services.TgChatService;
import edu.java.services.jdbc.JdbcLinkService;
import edu.java.services.jdbc.JdbcLinkUpdater;
import edu.java.services.jdbc.JdbcTgChatService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@AllArgsConstructor
public class JdbcAccessConfiguration {
    @Bean
    public LinkService jdbcLinkService(JdbcLinksDao linksDao) {
        return new JdbcLinkService(linksDao);
    }

    @Bean
    public TgChatService jdbcTgChatService(JdbcTgChatDao chatDao) {
        return new JdbcTgChatService(chatDao);
    }

    @Bean
    public LinkUpdater jdbcLinkUpdater(
        JdbcLinksDao linksDao,
        JdbcTgChatDao chatDao,
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        BotClient botClient
    ) {
        return new JdbcLinkUpdater(linksDao, chatDao, gitHubClient, stackOverflowClient, botClient);
    }
}
