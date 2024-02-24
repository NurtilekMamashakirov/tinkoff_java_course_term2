package edu.java.bot.Listeners;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.UpdateHandlers.CommandHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyUpdatesListener implements UpdatesListener {
    private TelegramBot bot;
    private List<CommandHandler> handlers;

    @Autowired
    public MyUpdatesListener(TelegramBot bot, List<CommandHandler> handlers) {
        this.bot = bot;
        this.handlers = handlers;
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            SendMessage answer =
                handlers.stream()
                    .filter(handler -> handler.supports(update))
                    .findFirst().get().handle(update);
            bot.execute(answer);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
