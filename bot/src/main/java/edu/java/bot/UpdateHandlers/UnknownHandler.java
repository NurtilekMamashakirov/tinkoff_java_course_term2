package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class UnknownHandler implements CommandHandler {

    private static final String UNKNOWN_COMMAND = "Команда неизвестна.";

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, UNKNOWN_COMMAND);
    }

    @Override
    public boolean supports(Update update) {
        return true;
    }

}
