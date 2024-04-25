package edu.java.bot.updateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UntrackHandler implements CommandHandler {
    private ScrapperClient scrapperClient;
    private final static String COMMAND = "/untrack";
    private final static Pattern COMMAND_PATTERN = Pattern.compile("/untrack (.*)");
    private final static String UNAUTHORIZED_USER_MESSAGE = "Используйте /start, чтобы авторизоваться";
    private final static String SUCCESSFUL_DELETE_MESSAGE = "Ссылка больше не отслеживается";
    private final static String ERROR_DELETE_MESSAGE =
        "Такой ссылки не найдено. \nИспользуйте /list, чтобы посмотреть список отслеживаемых ссылок";

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        String url = update.message().text().split(" ")[1];
        if (!scrapperClient.checkIfChatExist(chatId)) {
            return new SendMessage(chatId, UNAUTHORIZED_USER_MESSAGE);
        }
        if (!scrapperClient.checkIfLinkExist(chatId, url)) {
            return new SendMessage(chatId, ERROR_DELETE_MESSAGE);
        }
        scrapperClient.removeLink(chatId, url);
        return new SendMessage(chatId, SUCCESSFUL_DELETE_MESSAGE);
    }

    @Override
    public boolean supports(Update update) {
        Matcher matcher = COMMAND_PATTERN.matcher(update.message().text());
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

}
