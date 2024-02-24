package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnknownHandler implements UpdateHandler {

    TelegramBot bot;

    @Autowired
    public UnknownHandler(TelegramBot bot) {
        this.bot = bot;
    }

    @Override public void handle(Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, UpdateHandler.UNKNOWN_COMMAND));
    }

    @Override
    public boolean supports(Update update) {
        return true;
    }

}
