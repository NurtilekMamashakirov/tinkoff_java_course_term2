package edu.java.repository.jooq.impls;

import edu.java.dto.models.Link;
import edu.java.repository.jdbc.LinkRepository;
import java.time.OffsetDateTime;
import java.util.List;

public class JooqLinkRepository implements LinkRepository {
    @Override
    public List<Link> getLinks(Long id) {
        return null;
    }

    @Override
    public Link deleteLink(Long id, String link) {
        return null;
    }

    @Override
    public Link addLink(Long id, String link) {
        return null;
    }

    @Override
    public List<Link> getLastNLinks(Integer numOfLinksToReturn) {
        return null;
    }

    @Override
    public void updateCheckedTime(String link) {

    }

    @Override
    public void updateUpdatedTime(String link, OffsetDateTime updatedAt) {

    }
}
