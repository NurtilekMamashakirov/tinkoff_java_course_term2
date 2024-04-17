package edu.java.repository;

public interface UsersDao {
    boolean deleteChat(Long id);

    boolean addChat(Long id);

    boolean chatExist(Long id);
}
