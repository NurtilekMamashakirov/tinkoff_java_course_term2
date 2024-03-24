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
    public void register(long tgChatId) {
        tgChatDao.addChat(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        tgChatDao.deleteChat(tgChatId);
    }
}
