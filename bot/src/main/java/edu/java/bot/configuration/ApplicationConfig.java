package edu.java.bot.configuration;

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
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    @NotEmpty
    Retry retry
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

    @Bean
    public Retry retry() {
        return retry;
    }

    public record Retry(
        RetryType type,
        Integer maxAttempts,
        Duration delay
    ) {
    }

    public enum RetryType {
        CONST, LINEAR, EXPONENTIAL
    }

}
