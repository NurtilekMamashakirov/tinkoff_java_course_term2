package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dataBase.UsersDataBase;
import java.util.regex.Pattern;

public class StartHandler implements UpdateHandler {

    private UpdateHandler next;
    private Update update;
    private TelegramBot bot;
    private Pattern commandPattern = Pattern.compile("^/start$");
    private final String HELLO_MESSAGE =
        "Добро пожаловать в бот, который позволит отслеживать состояние запроса на сайте GitHub или StackOverFlow";
    private final String ALREADY_AUTHORIZED = "Бот уже запущен";

    public StartHandler(TelegramBot bot, UpdateHandler next, Update update) {
        this.next = next;
        this.bot = bot;
        this.update = update;
    }

    @Override
    public void handle() {
        if (commandPattern.matcher(update.message().text()).matches()) {
            User user = UsersDataBase.getUserById(update.message().from().id());
            Long chatId = update.message().chat().id();
            if (user != null) {
                bot.execute(new SendMessage(chatId, ALREADY_AUTHORIZED));
            } else {
                UsersDataBase.addUser(update.message().from());
                bot.execute(new SendMessage(chatId, HELLO_MESSAGE));
            }
        } else if (next != null) {
            next.handle();
        } else {
            bot.execute(new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND));
        }
    }

}
