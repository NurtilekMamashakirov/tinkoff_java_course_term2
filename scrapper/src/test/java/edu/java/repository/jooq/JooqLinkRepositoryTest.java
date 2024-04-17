package edu.java.repository.jooq;

import edu.java.dto.models.Link;
import edu.java.repository.jooq.impls.JooqLinkRepository;
import edu.java.repository.jooq.impls.JooqTgChatRepository;
import edu.java.scrapper.IntegrationEnvironment;
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
public class JooqLinkRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JooqLinkRepository linkRepository;
    @Autowired
    private JooqTgChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    void addLinkTest() {
        final Long chatId = 1L;
        final String link = "UwU";
        chatRepository.addChat(chatId);
        linkRepository.addLink(chatId, link);
        assertThat(jdbcTemplate.queryForObject("select count(*) from link where link.status = 1", Integer.class) ==
            1).isTrue();
        assertThat(jdbcTemplate.queryForObject("select count(*) from chat_and_link where status = 1", Integer.class) ==
            1).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void deleteLinkTest() {
        final Long chatId = 1L;
        final String link = "UwU";
        chatRepository.addChat(chatId);
        linkRepository.addLink(chatId, link);
        linkRepository.deleteLink(chatId, link);
        assertThat(jdbcTemplate.queryForObject("select count(*) from chat_and_link where status = 1", Integer.class) ==
            0).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void getLinksTest() {
        final Long chatId = 1L;
        final String url1 = "UwU";
        final String url2 = "uWu2";
        chatRepository.addChat(chatId);
        linkRepository.addLink(chatId, url1);
        linkRepository.addLink(chatId, url2);
        List<URI> urls = linkRepository
            .getLinks(chatId)
            .stream().map(Link::getUrl)
            .toList();
        assertThat(urls).containsAll(List.of(URI.create(url1), URI.create(url2)));
    }

    @Test
    @Transactional
    @Rollback
    void getLastNLinksTest() {
        final Long chatId = 1L;
        final String url1 = "UwU";
        final String url2 = "uWu2";
        chatRepository.addChat(chatId);
        linkRepository.addLink(chatId, url1);
        linkRepository.addLink(chatId, url2);
        assertThat(linkRepository.getLastNLinks(1).getFirst().getUrl().toString()).isEqualTo(url1);
    }
}
