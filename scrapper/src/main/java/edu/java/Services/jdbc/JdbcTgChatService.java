package edu.java.Services.jdbc;

import edu.java.Services.TgChatService;
import edu.java.repository.jdbc.JdbcTgChatDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JdbcTgChatService implements TgChatService {

    private JdbcTgChatDao tgChatDao;

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
