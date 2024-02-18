package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dataBase.UserAndLinksDataBase;
import edu.java.bot.dataBase.UsersDataBase;
import java.util.List;
import java.util.regex.Pattern;

public class ListHandler implements UpdateHandler {
    private UpdateHandler next;
    private Update update;
    private TelegramBot bot;
    private Pattern commandPattern = Pattern.compile("^/list$");
    private final String UNAUTHORIZED_USER_MESSAGE = "Используйте /start, чтобы авторизоваться";

    public ListHandler(TelegramBot bot, UpdateHandler next, Update update) {
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
                List<String> allLinksOfUser = UserAndLinksDataBase.getAllLinksById(user.id());
                bot.execute(new SendMessage(chatId, concatenateStrings(allLinksOfUser)));
            } else {
                bot.execute(new SendMessage(chatId, UNAUTHORIZED_USER_MESSAGE));
            }
        } else if (next != null) {
            next.handle();
        } else {
            bot.execute(new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND));
        }
    }

    private String concatenateStrings(List<String> list) {
        StringBuilder toReturn = new StringBuilder();
        for (String string : list) {
            toReturn.append(string + "\n");
        }
        return toReturn.toString();
    }
}
