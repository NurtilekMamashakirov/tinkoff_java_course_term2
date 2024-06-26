package edu.java.repository.jdbc;

import edu.java.dto.models.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
@AllArgsConstructor
public class JdbcLinkRepository implements LinkRepository {

    private JdbcTemplate jdbcTemplate;
    private final static String GET_ALL_LINKS_IDS_COMMAND = "SELECT id from link where status = 1";
    private final static String GET_LINKS_IDS_COMMAND =
        "SELECT link_id from chat_and_link where status = 1 and chat_id = ?";
    private final static String GET_LINK_STRING_BY_ID_COMMAND = "SELECT link.url from link WHERE id = ?";
    private final static String GET_LINK_CHECKED_AT_BY_ID_COMMAND = "SELECT link.checked_at from link WHERE id = ?";
    private final static String GET_LINK_UPDATED_AT_BY_ID_COMMAND = "SELECT link.updated_at from link WHERE id = ?";
    private final static String GET_LINK_ID_BY_STRING_COMMAND = "SELECT link.id from link where link.url = ?";
    private final static String GET_LINK_UPDATED_AT_BY_STRING_COMMAND =
        "SELECT link.updated_at from link where link.url = ?";
    private final static String GET_LINK_CHECKED_AT_BY_STRING_COMMAND =
        "SELECT link.checked_at from link where link.url = ?";

    private final static String DELETE_LINK_BY_CHAT_COMMAND =
        "UPDATE chat_and_link SET status = 0 where chat_id = ? and link_id = ?";
    private final static String CHECK_IF_LINK_EXIST_COMMAND = "SELECT COUNT(*) from link where url = ? and status = 1";
    private final static String CREATE_NEW_LINK_COMMAND =
        "INSERT INTO link (url, updated_at, checked_at, status) VALUES (?,?,?,1)";
    private final static String CHECK_IF_CHAT_HAS_LINK_COMMAND =
        "SELECT COUNT(*) from chat_and_link where chat_id = ? and link_id = ? and status = 1";
    private final static String ADD_LINK_TO_CHAT_COMMAND =
        "INSERT INTO chat_and_link (chat_id, link_id, status) VALUES (?,?,1)";
    private final static String GET_ALL_ACTIVE_LINKS_COMMAND = "SELECT from link where status = 1";
    private final static String UPDATE_CHECKED_TIME_COMMAND = "UPDATE link SET checked_at = ? where url = ?";
    private final static String UPDATE_UPDATED_TIME_COMMAND = "UPDATE link SET updated_at = ? where url = ?";

    @Override
    public List<Link> getLinks(Long id) {
        List<Long> linkIds = jdbcTemplate.queryForList(GET_LINKS_IDS_COMMAND, Long.class, id);
        List<Link> links = new ArrayList<>();
        for (Long linkId : linkIds) {
            String linkString = jdbcTemplate.queryForObject(GET_LINK_STRING_BY_ID_COMMAND, String.class, linkId);
            OffsetDateTime updatedAt =
                jdbcTemplate.queryForObject(GET_LINK_UPDATED_AT_BY_ID_COMMAND, OffsetDateTime.class, linkId);
            OffsetDateTime checkedAt =
                jdbcTemplate.queryForObject(GET_LINK_CHECKED_AT_BY_ID_COMMAND, OffsetDateTime.class, linkId);
            links.add(new Link(linkId, URI.create(linkString), updatedAt, checkedAt));
        }
        return links;
    }

    @Override
    public Link deleteLink(Long id, String link) {
        if (!checkIfLinkExist(link)) {
            return null; // link doesnt exist
        }
        Link linkToDelete = getLinkByString(link);
        jdbcTemplate.update(DELETE_LINK_BY_CHAT_COMMAND, id, linkToDelete.getId());
        return linkToDelete;
    }

    @Override
    public Link addLink(Long id, String link) {
        if (!checkIfLinkExist(link)) {
            createNewLink(link);
        }
        Link linkToAdd = getLinkByString(link);
        if (checkIfChatHasLink(id, linkToAdd.getId())) {
            return null; // chat already has this link
        }
        jdbcTemplate.update(ADD_LINK_TO_CHAT_COMMAND, id, linkToAdd.getId());
        return linkToAdd;
    }

    @Override
    public List<Link> getLastNLinks(Integer numOfLinksToReturn) {
        List<Long> linkIds = jdbcTemplate.queryForList(GET_ALL_LINKS_IDS_COMMAND, Long.class);
        List<Link> allLinks = new ArrayList<>();
        for (Long linkId : linkIds) {
            String url = jdbcTemplate.queryForObject(GET_LINK_STRING_BY_ID_COMMAND, String.class, linkId);
            OffsetDateTime checkedAt =
                jdbcTemplate.queryForObject(GET_LINK_CHECKED_AT_BY_ID_COMMAND, OffsetDateTime.class, linkId);
            OffsetDateTime updatedAt =
                jdbcTemplate.queryForObject(GET_LINK_UPDATED_AT_BY_ID_COMMAND, OffsetDateTime.class, linkId);
            allLinks.add(new Link(linkId, URI.create(url), updatedAt, checkedAt));
        }
        return allLinks
            .stream()
            .sorted(Comparator.comparing(Link::getCheckedAt))
            .limit(numOfLinksToReturn)
            .toList();
    }

    @Override
    public void updateCheckedTime(String link) {
        jdbcTemplate.update(UPDATE_CHECKED_TIME_COMMAND, OffsetDateTime.now(), link);
    }

    @Override
    public void updateUpdatedTime(String link, OffsetDateTime updatedAt) {
        jdbcTemplate.update(UPDATE_UPDATED_TIME_COMMAND, updatedAt, link);
    }

    private Link getLinkByString(String linkString) {
        Long id = jdbcTemplate.queryForObject(GET_LINK_ID_BY_STRING_COMMAND, Long.class, linkString);
        OffsetDateTime updatedAt =
            jdbcTemplate.queryForObject(
                GET_LINK_UPDATED_AT_BY_STRING_COMMAND,
                OffsetDateTime.class,
                linkString
            );
        OffsetDateTime checkedAt =
            jdbcTemplate.queryForObject(
                GET_LINK_CHECKED_AT_BY_STRING_COMMAND,
                OffsetDateTime.class,
                linkString
            );
        return new Link(id, URI.create(linkString), updatedAt, checkedAt);
    }

    private boolean checkIfLinkExist(String linkString) {
        return jdbcTemplate.queryForObject(CHECK_IF_LINK_EXIST_COMMAND, Integer.class, linkString) != 0;
    }

    private void createNewLink(String linkString) {
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        jdbcTemplate.update(CREATE_NEW_LINK_COMMAND, linkString, offsetDateTime, offsetDateTime);
    }

    private boolean checkIfChatHasLink(Long chatId, Long linkId) {
        return jdbcTemplate.queryForObject(CHECK_IF_CHAT_HAS_LINK_COMMAND, Integer.class, chatId, linkId) != 0;
    }

}
