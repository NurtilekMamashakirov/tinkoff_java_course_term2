package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.regex.Pattern;

public class HelpHandler implements UpdateHandler {

    private UpdateHandler next;
    private Update update;
    private TelegramBot bot;
    private Pattern commandPattern = Pattern.compile("^/help$");
    private final String COMMANDS_LIST =
        "/start -- зарегистрировать пользователя\n/help -- вывести окно с командами\n" +
            "/track -- начать отслеживание ссылки\n" + "/untrack -- прекратить отслеживание ссылки\n" +
            "/list -- показать список отслеживаемых ссылок";

    public HelpHandler(TelegramBot bot, UpdateHandler next, Update update) {
        this.next = next;
        this.bot = bot;
        this.update = update;
    }

    @Override
    public void handle() {
        if (commandPattern.matcher(update.message().text()).matches()) {
            Long chatId = update.message().chat().id();
            bot.execute(new SendMessage(chatId, COMMANDS_LIST));
        } else if (next != null) {
            next.handle();
        } else {
            bot.execute(new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND));
        }
    }
}
