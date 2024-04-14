package edu.java.repository.jdbc;

import edu.java.dto.models.Link;
import edu.java.scrapper.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JdbcLinkRepository linkRepository;
    @Autowired
    private JdbcTgChatRepository chatRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static String COUNT_LINKS_COMMAND = "SELECT COUNT(*) from link where status = 1";
    private final static String COUNT_CHAT_AND_LINKS_COMMAND = "SELECT COUNT(*) from chat_and_link where status = 1";

    @Test
    @Transactional
    @Rollback
    void addLinkTest() {
        String linkString = "exampleLink";
        Long chatId = 1L;
        chatRepository.addChat(chatId);
        linkRepository.addLink(chatId, linkString);
        Boolean isLinkCreated = jdbcTemplate.queryForObject(COUNT_LINKS_COMMAND, Integer.class) > 0;
        Boolean isLinkConnectedToChat = jdbcTemplate.queryForObject(COUNT_CHAT_AND_LINKS_COMMAND, Integer.class) > 0;
        assertThat(isLinkCreated & isLinkConnectedToChat).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        String linkString = "exampleLink";
        Long chatId = 1L;
        chatRepository.addChat(chatId);
        linkRepository.addLink(chatId, linkString);
        linkRepository.deleteLink(chatId, linkString);
        assertThat(jdbcTemplate.queryForObject(COUNT_CHAT_AND_LINKS_COMMAND, Integer.class) == 0).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void getLinksTest() {
        String linkString = "exampleLink";
        Long chatId = 1L;
        chatRepository.addChat(chatId);
        linkRepository.addLink(chatId, linkString);
        List<Link> links = linkRepository.getLinks(chatId);
        assertThat(links).isNotEmpty();
    }
}
