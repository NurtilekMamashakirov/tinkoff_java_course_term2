package edu.java.bot.updateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface CommandHandler {

    SendMessage handle(Update update);

    boolean supports(Update update);
}
