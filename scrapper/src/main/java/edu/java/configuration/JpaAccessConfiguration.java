package edu.java.configuration;

import edu.java.clients.bot.BotClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.handler.EventHandler;
import edu.java.clients.stackOverflow.StackOverflowClient;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.repository.jpa.JpaTgChatRepository;
import edu.java.services.LinkService;
import edu.java.services.LinkUpdater;
import edu.java.services.TgChatService;
import edu.java.services.jpa.JpaLinkService;
import edu.java.services.jpa.JpaLinkUpdater;
import edu.java.services.jpa.JpaTgChatService;
import edu.java.services.kafka.QueueProducer;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@AllArgsConstructor
public class JpaAccessConfiguration {
    @Bean
    public LinkService jpaLinkService(
        JpaTgChatRepository chatRepository,
        JpaLinkRepository linkRepository
    ) {
        return new JpaLinkService(linkRepository, chatRepository);
    }

    @Bean
    public TgChatService jpaTgChatService(JpaTgChatRepository chatRepository) {
        return new JpaTgChatService(chatRepository);
    }

    @Bean
    public LinkUpdater jpaLinkUpdater(
        JpaLinkRepository linkRepository,
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        BotClient botClient,
        List<EventHandler> eventHandlers,
        ApplicationConfig applicationConfig,
        QueueProducer queueProducer
    ) {
        return new JpaLinkUpdater(
            linkRepository,
            gitHubClient,
            stackOverflowClient,
            botClient,
            eventHandlers,
            applicationConfig,
            queueProducer
        );
    }

}
