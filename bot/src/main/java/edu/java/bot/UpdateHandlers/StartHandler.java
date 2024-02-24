package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dataBase.UsersDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class StartHandler implements UpdateHandler {
    private TelegramBot bot;
    private Pattern commandPattern = Pattern.compile("^/start$");
    private final String HELLO_MESSAGE =
        "Добро пожаловать в бот, который позволит отслеживать состояние запроса на сайте GitHub или StackOverFlow";
    private final String ALREADY_AUTHORIZED = "Бот уже запущен";

    @Autowired
    public StartHandler(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update) {
        User user = UsersDataBase.getUserById(update.message().from().id());
        Long chatId = update.message().chat().id();
        if (user != null) {
            bot.execute(new SendMessage(chatId, ALREADY_AUTHORIZED));
        } else {
            UsersDataBase.addUser(update.message().from());
            bot.execute(new SendMessage(chatId, HELLO_MESSAGE));
        }
    }

    @Override
    public boolean supports(Update update) {
        if (commandPattern.matcher(update.message().text()).matches()) {
            return true;
        }
        return false;
    }

}
