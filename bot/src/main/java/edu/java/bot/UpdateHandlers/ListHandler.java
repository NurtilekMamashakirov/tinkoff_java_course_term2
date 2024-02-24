package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dataBase.UserAndLinksDataBase;
import edu.java.bot.dataBase.UsersDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class ListHandler implements UpdateHandler {
    private TelegramBot bot;
    private Pattern commandPattern = Pattern.compile("^/list$");
    private final String UNAUTHORIZED_USER_MESSAGE = "Используйте /start, чтобы авторизоваться";

    @Autowired
    public ListHandler(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update) {
        if (commandPattern.matcher(update.message().text()).matches()) {
            User user = UsersDataBase.getUserById(update.message().from().id());
            Long chatId = update.message().chat().id();
            if (user != null) {
                List<String> allLinksOfUser = UserAndLinksDataBase.getAllLinksById(user.id());
                bot.execute(new SendMessage(chatId, concatenateStrings(allLinksOfUser)));
            } else {
                bot.execute(new SendMessage(chatId, UNAUTHORIZED_USER_MESSAGE));
            }
        }
    }

    @Override
    public boolean supports(Update update) {
        if (commandPattern.matcher(update.message().text()).matches()) {
            return true;
        }
        return false;
    }

    private String concatenateStrings(List<String> list) {
        StringBuilder toReturn = new StringBuilder();
        for (String string : list) {
            toReturn.append(string + "\n");
        }
        return toReturn.toString();
    }
}
