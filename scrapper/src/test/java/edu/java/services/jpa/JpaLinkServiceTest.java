package edu.java.services.jpa;

import edu.java.dto.models.Link;
import edu.java.scrapper.IntegrationEnvironment;
import edu.java.services.LinkService;
import edu.java.services.TgChatService;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaLinkServiceTest extends IntegrationEnvironment {
    @Autowired
    private LinkService linkService;
    @Autowired
    private TgChatService chatService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final static long ID = 1L;
    private final static URI url =
        URI.create("https://api.github.com/repos/NurtilekMamashakirov/tinkoff_java_course_term2/pulls/1");
    private static final String checkLinkSQL = "select count(*) from link";
    private static final String checkChatAndLinkSQL = "select count(*) from chat_and_link";

    @Test
    @Transactional
    @Rollback
    void addTestLinkTable() {
        chatService.register(ID);
        linkService.add(ID, url);
        assertThat(jdbcTemplate.queryForObject(checkLinkSQL, Integer.class) == 1);
    }

    @Test
    @Transactional
    @Rollback
    void addTestChatAndLinkTable() {
        chatService.register(ID);
        linkService.add(ID, url);
        assertThat(jdbcTemplate.queryForObject(checkChatAndLinkSQL, Integer.class) == 1);
    }

    @Test
    @Transactional
    @Rollback
    void listAllTest() {
        chatService.register(ID);
        linkService.add(ID, url);
        List<Link> linksOfChat = linkService.listAll(ID);
        assertThat(linksOfChat.get(0).getUrl()).isEqualTo(url);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        chatService.register(ID);
        linkService.add(ID, url);
        linkService.remove(ID, url);
        assertThat(linkService.listAll(ID)).isEmpty();
    }
}
