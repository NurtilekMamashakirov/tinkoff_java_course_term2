package edu.java.services.jooq;

import edu.java.repository.jooq.impls.JooqTgChatRepository;
import edu.java.services.TgChatService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JooqTgChatService implements TgChatService {
    private JooqTgChatRepository chatRepository;

    @Override
    public boolean register(long tgChatId) {
        return chatRepository.addChat(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        chatRepository.deleteChat(tgChatId);
    }

    @Override
    public boolean checkIfChatExist(long tgChatId) {
        return chatRepository.chatExist(tgChatId);
    }
}
