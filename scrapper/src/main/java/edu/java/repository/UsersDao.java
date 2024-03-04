package edu.java.repository;

import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;

public interface UsersDao {
    boolean deleteChat(Long id);

    boolean addChat(Long id);

    boolean chatExist(Long id);
}
