package edu.java.bot.updateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class StartHandler implements CommandHandler {

    private ScrapperClient scrapperClient;
    private final static String COMMAND = "/start";
    private final static String HELLO_MESSAGE =
        "Добро пожаловать в бот, который позволит отслеживать состояние запроса на сайте GitHub или StackOverFlow.";
    private final static String ALREADY_AUTHORIZED = "Бот уже запущен";

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        if (scrapperClient.checkIfChatExist(chatId)) {
            return new SendMessage(chatId, ALREADY_AUTHORIZED);
        }
        scrapperClient.addChat(update.message().from().id());
        return new SendMessage(chatId, HELLO_MESSAGE);
    }

    @Override
    public boolean supports(Update update) {
        if (update.message().text().equalsIgnoreCase(COMMAND)) {
            return true;
        }
        return false;
    }

}
