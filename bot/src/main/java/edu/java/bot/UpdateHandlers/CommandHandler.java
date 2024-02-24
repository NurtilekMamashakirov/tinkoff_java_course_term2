package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.model.Update;

public interface UpdateHandler {

    String UNKNOWN_COMMAND = "Команда неизвестна.";

    void handle(Update update);

    boolean supports(Update update);
}
