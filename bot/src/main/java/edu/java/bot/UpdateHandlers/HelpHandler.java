package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class HelpHandler implements UpdateHandler {
    private TelegramBot bot;
    private Pattern commandPattern = Pattern.compile("^/help$");
    private final String COMMANDS_LIST =
        "/start -- зарегистрировать пользователя\n/help -- вывести окно с командами\n" +
            "/track -- начать отслеживание ссылки\n" + "/untrack -- прекратить отслеживание ссылки\n" +
            "/list -- показать список отслеживаемых ссылок";

    @Autowired
    public HelpHandler(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, COMMANDS_LIST));
    }

    @Override
    public boolean supports(Update update) {
        if (commandPattern.matcher(update.message().text()).matches()) {
            return true;
        }
        return false;
    }
}
