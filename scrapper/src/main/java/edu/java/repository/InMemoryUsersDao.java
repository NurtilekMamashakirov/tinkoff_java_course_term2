package edu.java.repository;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryUsersDao implements UsersDao {

    private final Set<Long> ids;

    public InMemoryUsersDao() {
        ids = new HashSet<>();
    }

    public InMemoryUsersDao(Set<Long> ids) {
        this.ids = ids;
    }

    @Override
    public boolean deleteChat(Long id) {
        return ids.remove(id);
    }

    @Override
    public boolean addChat(Long id) {
        return ids.add(id);
    }

    @Override
    public boolean chatExist(Long id) {
        return ids.contains(id);
    }
}
