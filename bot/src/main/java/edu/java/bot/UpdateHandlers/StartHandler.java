package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dataBase.UsersDataBase;
import org.springframework.stereotype.Component;

public class StartHandler implements CommandHandler {
    private final static String COMMAND = "/start";
    private final String HELLO_MESSAGE =
        "Добро пожаловать в бот, который позволит отслеживать состояние запроса на сайте GitHub или StackOverFlow.";
    private final String ALREADY_AUTHORIZED = "Бот уже запущен";

    @Override
    public SendMessage handle(Update update) {
        User user = UsersDataBase.getUserById(update.message().from().id());
        Long chatId = update.message().chat().id();
        if (user != null) {
            return new SendMessage(chatId, ALREADY_AUTHORIZED);
        } else {
            UsersDataBase.addUser(update.message().from());
            return new SendMessage(chatId, HELLO_MESSAGE);
        }
    }

    @Override
    public boolean supports(Update update) {
        if (update.message().text().equalsIgnoreCase(COMMAND)) {
            return true;
        }
        return false;
    }

}
