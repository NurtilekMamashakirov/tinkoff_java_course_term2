package edu.java.repository.jooq.impls;

import edu.java.dto.models.Link;
import edu.java.repository.jdbc.LinkRepository;
import edu.java.repository.jooq.tables.ChatAndLink;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.repository.jooq.Tables.CHAT_AND_LINK;
import static edu.java.repository.jooq.Tables.LINK;

@Repository
@AllArgsConstructor
public class JooqLinkRepository implements LinkRepository {

    private DSLContext context;

    @Override
    public List<Link> getLinks(Long id) {
        return context
            .select()
            .from(CHAT_AND_LINK)
            .join(LINK)
            .on(CHAT_AND_LINK.LINK_ID.eq(LINK.ID))
            .where(CHAT_AND_LINK.CHAT_ID.eq(id))
            .and(CHAT_AND_LINK.STATUS.eq(1))
            .fetchInto(Link.class);
    }

    @Override
    public Link deleteLink(Long id, String link) {
        if (!checkIfLinkExist(link)) {
            return null; // link doesnt exist
        }
        Link linkToDelete = context
            .selectFrom(LINK)
            .where(LINK.URL.eq(link))
            .fetchInto(Link.class)
            .getFirst();
        context.update(CHAT_AND_LINK)
            .set(CHAT_AND_LINK.STATUS, 0)
            .where(CHAT_AND_LINK.CHAT_ID.eq(id))
            .and(CHAT_AND_LINK.LINK_ID.eq(linkToDelete.getId()))
            .execute();
        return linkToDelete;
    }

    @Override
    public Link addLink(Long id, String link) {
        if (!checkIfLinkExist(link)) {
            createNewLink(link);
        }
        Link linkToAdd = context
            .selectFrom(LINK)
            .where(LINK.URL.eq(link))
            .fetchInto(Link.class)
            .getFirst();
        if (checkIfChatHasLink(id, linkToAdd.getId())) {
            return null; //chat already has this link
        }
        context.insertInto(CHAT_AND_LINK, CHAT_AND_LINK.LINK_ID, CHAT_AND_LINK.CHAT_ID)
            .values(linkToAdd.getId(), id)
            .execute();
        return linkToAdd;
    }

    @Override
    public List<Link> getLastNLinks(Integer numOfLinksToReturn) {
        List<Link> allLinks = context
            .selectFrom(LINK)
            .where(LINK.STATUS.eq(1))
            .fetchInto(Link.class);
        List<Link> linksToReturn = allLinks
            .stream()
            .sorted(Comparator.comparing(Link::getUpdatedAt))
            .limit(numOfLinksToReturn)
            .toList();
        return linksToReturn;
    }

    @Override
    public void updateCheckedTime(String link) {
        context.update(LINK)
            .set(LINK.CHECKED_AT, OffsetDateTime.now())
            .where(LINK.URL.eq(link))
            .execute();
    }

    @Override
    public void updateUpdatedTime(String link, OffsetDateTime updatedAt) {
        context.update(LINK)
            .set(LINK.UPDATED_AT, updatedAt)
            .where(LINK.URL.eq(link))
            .execute();
    }

    private boolean checkIfLinkExist(String link) {
        List<Link> links = context
            .selectFrom(LINK)
            .where(LINK.URL.eq(link))
            .and(LINK.STATUS.eq(1))
            .fetchInto(Link.class);
        return !links.isEmpty();
    }

    private void createNewLink(String link) {
        context
            .insertInto(LINK, LINK.URL, LINK.UPDATED_AT, LINK.CHECKED_AT)
            .values(link, OffsetDateTime.now(), OffsetDateTime.now())
            .execute();
    }

    private boolean checkIfChatHasLink(Long chatId, Long linkId) {
        List<ChatAndLink> chatAndLinks = context
            .select()
            .from(CHAT_AND_LINK)
            .where(CHAT_AND_LINK.LINK_ID.eq(linkId))
            .and(CHAT_AND_LINK.CHAT_ID.eq(chatId))
            .fetchInto(ChatAndLink.class);
        return !chatAndLinks.isEmpty();
    }
}
