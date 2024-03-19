package edu.java.repository;

import edu.java.dto.models.Link;
import java.util.List;

public interface LinksDao {
    List<Link> getLinks(Long id);

    boolean deleteLink(Long id, String link);

    boolean addLink(Long id, String link);
}
