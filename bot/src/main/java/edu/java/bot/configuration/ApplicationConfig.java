package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.UpdateHandlers.HelpHandler;
import edu.java.bot.UpdateHandlers.ListHandler;
import edu.java.bot.UpdateHandlers.StartHandler;
import edu.java.bot.UpdateHandlers.TrackHandler;
import edu.java.bot.UpdateHandlers.UntrackHandler;
import edu.java.bot.UpdateHandlers.UpdateHandler;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.List;

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
    public List<UpdateHandler> handlers() {
        List<UpdateHandler> handlers = new ArrayList<>();
        handlers.add(new HelpHandler(bot()));
        handlers.add(new StartHandler(bot()));
        handlers.add(new ListHandler(bot()));
        handlers.add(new TrackHandler(bot()));
        handlers.add(new UntrackHandler(bot()));
        return handlers;
    }

}
