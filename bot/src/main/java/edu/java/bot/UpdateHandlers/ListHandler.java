package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dataBase.UserAndLinksDataBase;
import edu.java.bot.dataBase.UsersDataBase;
import java.util.List;

public class ListHandler implements CommandHandler {
    private final static String COMMAND = "/list";
    private final static String UNAUTHORIZED_USER_MESSAGE = "Используйте /start, чтобы авторизоваться";
    private final static String NO_LINKS_MESSAGE = "У вас не отслеживаемых ссылок. \n"
        + "Используйте /track, чтобы их добавить.";

    @Override

    public SendMessage handle(Update update) {
        User user = UsersDataBase.getUserById(update.message().from().id());
        Long chatId = update.message().chat().id();
        if (user != null) {
            List<String> allLinksOfUser = UserAndLinksDataBase.getAllLinksById(user.id());
            if (allLinksOfUser.isEmpty()) {
                return new SendMessage(chatId, NO_LINKS_MESSAGE);
            }
            return new SendMessage(chatId, formAnswer(allLinksOfUser));
        } else {
            return new SendMessage(chatId, UNAUTHORIZED_USER_MESSAGE);
        }

    }

    @Override
    public boolean supports(Update update) {
        if (update.message().text().equalsIgnoreCase(COMMAND)) {
            return true;
        }
        return false;
    }

    private String formAnswer(List<String> list) {
        StringBuilder toReturn = new StringBuilder();
        for (String string : list) {
            toReturn.append(string + "\n");
        }
        return toReturn.toString();
    }
}
