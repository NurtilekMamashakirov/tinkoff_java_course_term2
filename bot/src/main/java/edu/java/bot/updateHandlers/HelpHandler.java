package edu.java.bot.updateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class HelpHandler implements CommandHandler {
    private final static String COMMAND = "/help";
    private final static String COMMANDS_LIST =
        "/start -- зарегистрировать пользователя\n/help -- вывести окно с командами\n"
            + "/track -- начать отслеживание ссылки\n" + "/untrack -- прекратить отслеживание ссылки\n"
            + "/list -- показать список отслеживаемых ссылок";

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        return new SendMessage(chatId, COMMANDS_LIST);
    }

    @Override
    public boolean supports(Update update) {
        if (update.message().text().equalsIgnoreCase(COMMAND)) {
            return true;
        }
        return false;
    }
}
