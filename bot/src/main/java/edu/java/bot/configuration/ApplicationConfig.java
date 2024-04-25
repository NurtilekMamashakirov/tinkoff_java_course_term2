package edu.java.bot.configuration;

import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.updateHandlers.CommandHandler;
import edu.java.bot.updateHandlers.HelpHandler;
import edu.java.bot.updateHandlers.ListHandler;
import edu.java.bot.updateHandlers.StartHandler;
import edu.java.bot.updateHandlers.TrackHandler;
import edu.java.bot.updateHandlers.UnknownHandler;
import edu.java.bot.updateHandlers.UntrackHandler;
import edu.java.bot.clients.ScrapperClient;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    @NotEmpty
    Kafka kafka
) {

    @Bean
    public TelegramBot bot() {
        return new TelegramBot(telegramToken);
    }

    @Bean
    public List<CommandHandler> handlers() {
        List<CommandHandler> handlers = new ArrayList<>();
        handlers.add(new HelpHandler());
        handlers.add(new UntrackHandler(scrapperClient()));
        handlers.add(new StartHandler(scrapperClient()));
        handlers.add(new ListHandler(scrapperClient()));
        handlers.add(new TrackHandler(scrapperClient()));
        handlers.add(new UnknownHandler());
        return handlers;
    }

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClient();
    }

    public record Kafka(
        String bootstrapServer,
        String groupId,
        String autoOffsetReset,
        Integer maxPollIntervalMs,
        Boolean enableAutoCommit,
        Integer concurrency,
        String topic
    ) {
    }

}
