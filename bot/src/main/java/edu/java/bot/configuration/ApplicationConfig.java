package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.UpdateHandlers.CommandHandler;
import edu.java.bot.UpdateHandlers.HelpHandler;
import edu.java.bot.UpdateHandlers.ListHandler;
import edu.java.bot.UpdateHandlers.StartHandler;
import edu.java.bot.UpdateHandlers.TrackHandler;
import edu.java.bot.UpdateHandlers.UnknownHandler;
import edu.java.bot.UpdateHandlers.UntrackHandler;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken
) {

    @Bean
    public TelegramBot bot() {
        return new TelegramBot(telegramToken);
    }

    @Bean
    public List<CommandHandler> handlers() {
        List<CommandHandler> handlers = new ArrayList<>();
        handlers.add(new HelpHandler());
        handlers.add(new UntrackHandler());
        handlers.add(new StartHandler());
        handlers.add(new ListHandler());
        handlers.add(new TrackHandler());
        handlers.add(new UnknownHandler());
        return handlers;
    }

}
