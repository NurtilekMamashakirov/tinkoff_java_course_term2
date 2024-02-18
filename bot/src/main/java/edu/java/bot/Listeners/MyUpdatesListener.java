package edu.java.bot.Listeners;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.UpdateHandlers.HelpHandler;
import edu.java.bot.UpdateHandlers.ListHandler;
import edu.java.bot.UpdateHandlers.StartHandler;
import edu.java.bot.UpdateHandlers.TrackHandler;
import edu.java.bot.UpdateHandlers.UntrackHandler;
import java.util.List;

public class MyUpdatesListener implements UpdatesListener {

    TelegramBot bot;

    public MyUpdatesListener(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            StartHandler startHandler = new StartHandler(bot, null, update);
            HelpHandler helpHandler = new HelpHandler(bot, startHandler, update);
            TrackHandler trackHandler = new TrackHandler(bot, helpHandler, update);
            UntrackHandler untrackHandler = new UntrackHandler(bot, trackHandler, update);
            ListHandler listHandler = new ListHandler(bot, untrackHandler, update);
            listHandler.handle();
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
