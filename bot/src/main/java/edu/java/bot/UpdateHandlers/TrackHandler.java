package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dataBase.UserAndLinksDataBase;
import edu.java.bot.dataBase.UsersDataBase;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrackHandler implements CommandHandler {
    private final static String COMMAND = "/track";
    private final static Pattern COMMAND_PATTERN =
        Pattern.compile("^/track (https://stackoverflow\\.com.*|https://github\\.com.*)");
    private final static String UNAUTHORIZED_USER_MESSAGE = "Используйте /start, чтобы авторизоваться";
    private final static String NOT_PERMITTED_LINK = "Неверная ссылка";
    private final static String SUCCESSFUL_ADD_MESSAGE = "Ссылка успешно добавлена";

    @Override
    public SendMessage handle(Update update) {
        User user = UsersDataBase.getUserById(update.message().from().id());
        Long chatId = update.message().chat().id();
        Matcher matcher = COMMAND_PATTERN.matcher(update.message().text());
        if (!matcher.matches()) {
            return new SendMessage(chatId, NOT_PERMITTED_LINK);
        }
        String url = matcher.group(1);
        if (user != null) {
            UserAndLinksDataBase.addLinkToUser(user.id(), url);
            return new SendMessage(chatId, SUCCESSFUL_ADD_MESSAGE);
        } else {
            return new SendMessage(chatId, UNAUTHORIZED_USER_MESSAGE);
        }
    }

    @Override
    public boolean supports(Update update) {
        String text = update.message().text();
        if (text.startsWith(COMMAND)) {
            return true;
        }
        return false;
    }

}
