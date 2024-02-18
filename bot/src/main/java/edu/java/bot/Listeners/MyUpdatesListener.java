package edu.java.bot.Listeners;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import java.util.List;

public class MyUpdatesListener implements UpdatesListener {

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
