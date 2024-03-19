package edu.java.repository;

import edu.java.dto.models.Link;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinksDao {
    List<Link> getLinks(Long id);

    boolean deleteLink(Long id, String link);

    boolean addLink(Long id, String link);

    List<Link> getLastNLinks(Integer numOfLinksToReturn); // Возвращает n самых давно не проверенных ссылок

    void updateCheckedTime(String link);

    void updateUpdatedTime(String link, OffsetDateTime updatedAt);
}
