package edu.java.repository.jdbc;

import edu.java.repository.TgChatDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class JdbcTgChatDao implements TgChatDao {

    private JdbcTemplate jdbcTemplate;
    private static final String STATUS_UPDATE_COMMAND = "UPDATE chat SET status = ? WHERE id = ?";
    private static final String ADD_CHAT_COMMAND = "INSERT INTO chat (id, status) VALUES (?, 1)";
    private static final String CHECK_FOR_EXIST_COMMAND = "SELECT COUNT(*) from chat where id = ? and status = 1";
    private static final int DELETE_STATUS = 0;
    private static final int ACTIVE_STATUS = 1;

    @Override
    public boolean deleteChat(Long id) {
        if (!chatExist(id)) {
            return false; // chat doesn't exist
        }
        jdbcTemplate.update(STATUS_UPDATE_COMMAND, DELETE_STATUS, id);
        return true;
    }

    @Override
    public boolean addChat(Long id) {
        if (chatExist(id)) {
            return false; // chat already exist
        }
        jdbcTemplate.update(ADD_CHAT_COMMAND, id);
        return true;
    }

    @Override
    public boolean chatExist(Long id) {
        return jdbcTemplate.queryForObject(CHECK_FOR_EXIST_COMMAND, Integer.class, id) != 0;
    }
}
