package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.request.LinkUpdate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BotServiceImpl implements BotService {
    private static final String UPDATE_MESSAGE = "Поступило обновление по ссылке ";
    private TelegramBot bot;

    @Override
    public void handleUpdates(LinkUpdate linkUpdate) {
        for (Integer chatId : linkUpdate.tgChatIds()) {
            SendMessage message = new SendMessage(linkUpdate.id(), UPDATE_MESSAGE + linkUpdate.url().toString());
            bot.execute(message);
        }
    }
}
