package edu.java.bot.Listeners;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.UpdateHandlers.UpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;

@Component
public class MyUpdatesListener implements UpdatesListener {
    TelegramBot bot;
    List<UpdateHandler> handlers;

    @Autowired
    public MyUpdatesListener(TelegramBot bot, List<UpdateHandler> handlers) {
        this.bot = bot;
        this.handlers = handlers;
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            long check = handlers.stream()
                .filter(handler -> handler.supports(update))
                .count();
            if (check == 1) {
                handlers.stream()
                    .filter(handler -> handler.supports(update))
                    .findAny()
                    .get()
                    .handle(update);
            } else {

            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
