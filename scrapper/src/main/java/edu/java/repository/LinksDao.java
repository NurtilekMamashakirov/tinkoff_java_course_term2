package edu.java.repository;

import java.util.List;

public interface LinksDao {
    List<String> getLinks(Long id);

    boolean deleteLink(Long id, String link);

    boolean addLink(Long id, String link);
}
