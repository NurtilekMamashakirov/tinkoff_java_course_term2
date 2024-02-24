package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dataBase.UserAndLinksDataBase;
import edu.java.bot.dataBase.UsersDataBase;
import edu.java.bot.utils.LinkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TrackHandler implements UpdateHandler {
    private TelegramBot bot;
    private Pattern commandPattern = Pattern.compile("^/track (.*)$");
    private final String UNAUTHORIZED_USER_MESSAGE = "Используйте /start, чтобы авторизоваться";
    private final String NOT_PERMITTED_LINK = "Неверная ссылка";

    @Autowired
    public TrackHandler(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update) {
        Matcher matcher = commandPattern.matcher(update.message().text());
        matcher.matches();
        User user = UsersDataBase.getUserById(update.message().from().id());
        Long chatId = update.message().chat().id();
        if (user != null) {
            String URL = matcher.group();
            if (LinkUtils.checkLink(URL)) {
                UserAndLinksDataBase.addLinkToUser(user.id(), URL);
            } else {
                bot.execute(new SendMessage(chatId, NOT_PERMITTED_LINK));
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
