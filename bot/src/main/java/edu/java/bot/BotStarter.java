package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.listeners.MyUpdatesListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotStarter {

    private TelegramBot bot;
    private MyUpdatesListener myUpdatesListener;

    @Autowired
    public BotStarter(TelegramBot bot, MyUpdatesListener myUpdatesListener) {
        this.bot = bot;
        this.myUpdatesListener = myUpdatesListener;
        bot.setUpdatesListener(this.myUpdatesListener);
    }


}
