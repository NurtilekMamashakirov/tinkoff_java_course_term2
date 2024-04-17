package edu.java.repository.jdbc;

import edu.java.scrapper.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcTgChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcTgChatRepository chatRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String COUNT_CHATS_COMMAND = "SELECT COUNT(*) from chat where status = 1";

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Long chatId = 1L;
        chatRepository.addChat(chatId);
        assertThat(jdbcTemplate.queryForObject(COUNT_CHATS_COMMAND, Integer.class)).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Long chatId = 1L;
        chatRepository.addChat(chatId);
        chatRepository.deleteChat(chatId);
        assertThat(jdbcTemplate.queryForObject(COUNT_CHATS_COMMAND, Integer.class)).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    void chatExistTest() {
        Long chatId = 1L;
        chatRepository.addChat(chatId);
        Boolean expected = jdbcTemplate.queryForObject(COUNT_CHATS_COMMAND, Integer.class) > 0;
        assertThat(chatRepository.chatExist(chatId)).isEqualTo(expected);
    }
}
