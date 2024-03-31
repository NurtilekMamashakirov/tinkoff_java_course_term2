package edu.java.bot.UpdateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TrackHandler implements CommandHandler {
    private ScrapperClient scrapperClient;
    private final static String COMMAND = "/track";
    private final static Pattern COMMAND_GITHUB_PATTERN =
        Pattern.compile("^/track https://github\\.com/(.*)/(.*)$");
    private final static Pattern COMMAND_STACK_OVERFLOW_PATTERN =
        Pattern.compile("^/track https://stackoverflow\\.com/questions/(.*)/(.*)$");
    private final static String BASE_GITHUB_URL = "https://api.github.com/repos";
    private final static String BASE_STACK_OVERFLOW_URL = "https://api.stackexchange.com/2.3/questions";
    private final static String STACK_OVERFLOW_URL_PARAM = "?site=stackoverflow";
    private final static String UNAUTHORIZED_USER_MESSAGE = "Используйте /start, чтобы авторизоваться";
    private final static String NOT_PERMITTED_LINK = "Неверная ссылка";
    private final static String SUCCESSFUL_ADD_MESSAGE = "Ссылка успешно добавлена";
    private final static String LINK_ALREADY_EXIST_MESSAGE = "Такая ссылка была уже добавлена";

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        Matcher matcherGitHub = COMMAND_GITHUB_PATTERN.matcher(update.message().text());
        Matcher matcherStackOverflow = COMMAND_STACK_OVERFLOW_PATTERN.matcher(update.message().text());
        if (!matcherGitHub.matches() && !matcherStackOverflow.matches()) {
            return new SendMessage(chatId, NOT_PERMITTED_LINK);
        }
        String url = "";
        if (matcherGitHub.matches()) {
            url = BASE_GITHUB_URL + "/" + matcherGitHub.group(1) + "/" + matcherGitHub.group(2);
        }
        if (matcherStackOverflow.matches()) {
            url = BASE_STACK_OVERFLOW_URL + "/" +
                matcherStackOverflow.group(1) +
                STACK_OVERFLOW_URL_PARAM;
        }
        if (!scrapperClient.checkIfChatExist(chatId)) {
            return new SendMessage(chatId, UNAUTHORIZED_USER_MESSAGE);
        }
        if (scrapperClient.checkIfLinkExist(chatId, url)) {
            return new SendMessage(chatId, LINK_ALREADY_EXIST_MESSAGE);
        }
        scrapperClient.addLink(chatId, url);
        return new SendMessage(chatId, SUCCESSFUL_ADD_MESSAGE);
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
