package edu.java.services.jpa;

import edu.java.dto.entity.ChatEntity;
import edu.java.repository.jpa.JpaTgChatRepository;
import edu.java.services.TgChatService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class JpaTgChatService implements TgChatService {

    private JpaTgChatRepository chatRepository;

    @Override
    @Transactional
    public boolean register(long tgChatId) {
        ChatEntity chat = new ChatEntity(tgChatId);
        if (chatRepository.existsById(tgChatId)) {
            return false;
        }
        chatRepository.saveAndFlush(chat);
        return true;
    }

    @Override
    @Transactional
    public void unregister(long tgChatId) {
        chatRepository.deleteById(tgChatId);
        chatRepository.existsById(tgChatId);
    }

    @Override
    @Transactional
    public boolean checkIfChatExist(long tgChatId) {
        return chatRepository.existsById(tgChatId);
    }
}
