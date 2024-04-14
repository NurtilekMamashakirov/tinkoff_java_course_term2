package edu.java.services.jdbc;

import edu.java.repository.jdbc.JdbcTgChatRepository;
import edu.java.services.TgChatService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JdbcTgChatService implements TgChatService {

    private JdbcTgChatRepository tgChatDao;

    @Override
    public boolean register(long tgChatId) {
        return tgChatDao.addChat(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        tgChatDao.deleteChat(tgChatId);
    }

    @Override
    public boolean checkIfChatExist(long tgChatId) {
        return tgChatDao.chatExist(tgChatId);
    }
}
