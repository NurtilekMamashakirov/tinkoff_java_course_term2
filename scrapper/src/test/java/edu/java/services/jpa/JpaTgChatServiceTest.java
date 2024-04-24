package edu.java.services.jpa;

import edu.java.scrapper.IntegrationEnvironment;
import edu.java.services.TgChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaTgChatServiceTest extends IntegrationEnvironment {
    @Autowired
    private TgChatService chatService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String COUNT_CHATS_COMMAND = "SELECT count(*) from chat where status = 1";
    private static final long ID = 1L;

    @Test
    @Transactional
    @Rollback
    void registerTest() {
        chatService.register(ID);
        assertThat(jdbcTemplate.queryForObject(COUNT_CHATS_COMMAND, Integer.class) == 1).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void unregisterTest() {
        chatService.register(ID);
        chatService.unregister(ID);
        assertThat(jdbcTemplate.queryForObject(COUNT_CHATS_COMMAND, Integer.class) == 0).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void checkIfChatExistTest() {
        chatService.register(ID);
        assertThat(chatService.checkIfChatExist(ID)).isTrue();
    }
}
