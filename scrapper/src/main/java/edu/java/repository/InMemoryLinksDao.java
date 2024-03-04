package edu.java.repository;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class InMemoryLinksDao implements LinksDao {

    private HashMap<Long, List<String>> linksByUsers;

    public InMemoryLinksDao() {
        linksByUsers = new HashMap<>();
    }

    public InMemoryLinksDao(HashMap<Long, List<String>> linksByUsers) {
        this.linksByUsers = linksByUsers;
    }

    @Override
    public List<String> getLinks(Long id) {
        return linksByUsers.get(id);
    }

    @Override
    public boolean deleteLink(Long id, String link) {
        if (linksByUsers.containsKey(id)) {
            List<String> links = linksByUsers.get(id);
            return links.remove(link);
        }
        return false;
    }

    @Override
    public boolean addLink(Long id, String link) {
        if (linksByUsers.containsKey(id)) {
            List<String> links = linksByUsers.get(id);
            return links.add(link);
        }
        List<String> links = new ArrayList<>();
        links.add(link);
        linksByUsers.put(id, links);
        return true;
    }
}
