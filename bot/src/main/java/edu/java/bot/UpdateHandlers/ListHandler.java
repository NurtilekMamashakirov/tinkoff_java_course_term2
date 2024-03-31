package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ListHandler implements CommandHandler {
    private ScrapperClient scrapperClient;
    private final static String COMMAND = "/list";
    private final static String UNAUTHORIZED_USER_MESSAGE = "Используйте /start, чтобы авторизоваться";
    private final static String NO_LINKS_MESSAGE = "У вас не отслеживаемых ссылок. \n"
        + "Используйте /track, чтобы их добавить.";

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        if (scrapperClient.checkIfChatExist(chatId)) {
            List<String> allLinksOfUser = scrapperClient
                .getLinks(chatId)
                .links()
                .stream()
                .map(linkResponse -> linkResponse.url().toString())
                .toList();
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
