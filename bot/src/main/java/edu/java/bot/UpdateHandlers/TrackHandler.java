package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dataBase.UsersDataBase;
import edu.java.bot.utils.LinkUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrackHandler implements UpdateHandler {
    private UpdateHandler next;
    private Update update;
    private TelegramBot bot;
    private Pattern commandPattern = Pattern.compile("^/track (.*)$");
    private final String UNAUTHORIZED_USER_MESSAGE = "Используйте /start, чтобы авторизоваться";

    public TrackHandler(TelegramBot bot, UpdateHandler next, Update update) {
        this.next = next;
        this.bot = bot;
        this.update = update;
    }

    @Override
    public void handle() {
        Matcher matcher = commandPattern.matcher(update.message().text());
        if (matcher.matches()) {
            User user = UsersDataBase.getUserById(update.message().from().id());
            Long chatId = update.message().chat().id();
            if (user != null) {
                String URL = matcher.group();
                if (LinkUtils.checkLink(URL)) {

                }
            } else {

            }
        } else if (next != null) {
            next.handle();
        } else {
            bot.execute(new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND));
        }
    }

}
