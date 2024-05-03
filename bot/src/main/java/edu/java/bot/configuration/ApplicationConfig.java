package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.clients.ScrapperClient;
import edu.java.bot.retry.RetryTemplateFactory;
import edu.java.bot.updateHandlers.CommandHandler;
import edu.java.bot.updateHandlers.HelpHandler;
import edu.java.bot.updateHandlers.ListHandler;
import edu.java.bot.updateHandlers.StartHandler;
import edu.java.bot.updateHandlers.TrackHandler;
import edu.java.bot.updateHandlers.UnknownHandler;
import edu.java.bot.updateHandlers.UntrackHandler;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    Retry retry,
    ScrapperConfig scrapperConfig
) {

    @Bean
    public TelegramBot bot() {
        return new TelegramBot(telegramToken);
    }

    @Bean
    public List<CommandHandler> handlers(RetryTemplate retryTemplate) {
        List<CommandHandler> handlers = new ArrayList<>();
        handlers.add(new HelpHandler());
        handlers.add(new UntrackHandler(scrapperClient(retryTemplate)));
        handlers.add(new StartHandler(scrapperClient(retryTemplate)));
        handlers.add(new ListHandler(scrapperClient(retryTemplate)));
        handlers.add(new TrackHandler(scrapperClient(retryTemplate)));
        handlers.add(new UnknownHandler());
        return handlers;
    }

    @Bean
    public ScrapperClient scrapperClient(RetryTemplate retryTemplate) {
        return new ScrapperClient(
            retryTemplate,
            scrapperConfig.baseUrl,
            scrapperConfig.linksUri,
            scrapperConfig.chatUri
        );
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.retry", name = "type", havingValue = "const")
    public RetryTemplate constantRetry() {
        return RetryTemplateFactory.constant(
            retry.statusCodes,
            retry.maxAttempts,
            retry.initialInterval
        );
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.retry", name = "type", havingValue = "linear")
    public RetryTemplate linearRetry() {
        return RetryTemplateFactory.linear(
            retry.statusCodes,
            retry.maxAttempts,
            retry.initialInterval,
            retry.maxInterval
        );
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.retry", name = "type", havingValue = "exponential")
    public RetryTemplate exponentialRetry() {
        return RetryTemplateFactory.exponential(
            retry.statusCodes,
            retry.maxAttempts,
            retry.initialInterval,
            retry.multiplier,
            retry.maxInterval
        );
    }

    public record Retry(
        @DefaultValue("429")
        Set<Integer> statusCodes,
        @DefaultValue("const")
        Retry.Type type,
        @DefaultValue("3")
        Integer maxAttempts,
        @DefaultValue("10")
        Long initialInterval,
        @DefaultValue("10")
        Long maxInterval,
        @DefaultValue("2.78")
        Double multiplier
    ) {
        public enum Type {
            CONST, LINEAR, EXPONENTIAL
        }
    }

    public record ScrapperConfig(
        @DefaultValue("http://localhost:8080")
        String baseUrl,
        @DefaultValue("/links")
        String linksUri,
        @DefaultValue("/tg-chat")
        String chatUri
    ) {
    }

}
