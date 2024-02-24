package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dataBase.UserAndLinksDataBase;
import edu.java.bot.dataBase.UsersDataBase;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UntrackHandler implements UpdateHandler {
    private TelegramBot bot;
    private Pattern commandPattern = Pattern.compile("^/untrack (.*)$");
    private final String UNAUTHORIZED_USER_MESSAGE = "Используйте /start, чтобы авторизоваться";
    private final String SUCCESSFUL_DELETE_MESSAGE = "Ссылка больше не отслеживается";
    private final String ERROR_DELETE_MESSAGE =
        "Такой ссылки не найдено. \nИспользуйте /list, чтобы посмотреть список отслеживаемых ссылок";

    public UntrackHandler(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update) {
        Matcher matcher = commandPattern.matcher(update.message().text());
        matcher.matches();
        User user = UsersDataBase.getUserById(update.message().from().id());
        Long chatId = update.message().chat().id();
        String URL = matcher.group();
        if (user != null) {
            if (UserAndLinksDataBase.deleteLinkToUser(user.id(), URL)) {
                bot.execute(new SendMessage(chatId, SUCCESSFUL_DELETE_MESSAGE));
            } else {
                bot.execute(new SendMessage(chatId, ERROR_DELETE_MESSAGE));
            }
        } else {
            bot.execute(new SendMessage(chatId, UNAUTHORIZED_USER_MESSAGE));
        }
    }

    @Override
    public boolean supports(Update update) {
        Matcher matcher = commandPattern.matcher(update.message().text());
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

}
