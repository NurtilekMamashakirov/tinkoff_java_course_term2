package edu.java.configuration;

import edu.java.clients.bot.BotClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.handler.EventHandler;
import edu.java.clients.stackOverflow.StackOverflowClient;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcTgChatRepository;
import edu.java.services.LinkService;
import edu.java.services.LinkUpdater;
import edu.java.services.TgChatService;
import edu.java.services.jdbc.JdbcLinkService;
import edu.java.services.jdbc.JdbcLinkUpdater;
import edu.java.services.jdbc.JdbcTgChatService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@AllArgsConstructor
public class JdbcAccessConfiguration {
    @Bean
    public LinkService jdbcLinkService(JdbcLinkRepository linksDao) {
        return new JdbcLinkService(linksDao);
    }

    @Bean
    public TgChatService jdbcTgChatService(JdbcTgChatRepository chatDao) {
        return new JdbcTgChatService(chatDao);
    }

    @Bean
    public LinkUpdater jdbcLinkUpdater(
        JdbcLinkRepository linksDao,
        JdbcTgChatRepository chatDao,
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        BotClient botClient,
        List<EventHandler> eventHandlers
    ) {
        return new JdbcLinkUpdater(linksDao, chatDao, gitHubClient, stackOverflowClient, botClient, eventHandlers);
    }
}
