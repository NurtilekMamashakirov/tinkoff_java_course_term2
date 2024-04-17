package edu.java.repository.jooq;

import edu.java.repository.jooq.impls.JooqTgChatRepository;
import edu.java.scrapper.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JooqTgChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JooqTgChatRepository chatRepository;
    private static final Long CHAT_ID = 1L;
    private static final String COUNT_CHATS_COMMAND = "SELECT COUNT(*) from chat where status = 1";

    @Test
    @Transactional
    @Rollback
    void addChatTest() {
        chatRepository.addChat(CHAT_ID);
        Integer numOfChats = jdbcTemplate.queryForObject(COUNT_CHATS_COMMAND, Integer.class);
        Integer expectedNumOfChats = 1;
        assertThat(numOfChats).isEqualTo(expectedNumOfChats);
    }

    @Test
    @Transactional
    @Rollback
    void deleteChatTest() {
        chatRepository.addChat(CHAT_ID);
        chatRepository.deleteChat(CHAT_ID);
        Integer expectedNumOfChats = 0;
        Integer numOfChats = jdbcTemplate.queryForObject(COUNT_CHATS_COMMAND, Integer.class);
        assertThat(numOfChats).isEqualTo(expectedNumOfChats);
    }

    @Test
    @Transactional
    @Rollback
    void chatExistTest() {
        chatRepository.addChat(CHAT_ID);
        assertThat(chatRepository.chatExist(CHAT_ID)).isTrue();
    }
}
