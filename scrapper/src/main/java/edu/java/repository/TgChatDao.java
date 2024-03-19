package edu.java.repository;

public interface TgChatDao {
    boolean deleteChat(Long id);

    boolean addChat(Long id);

    boolean chatExist(Long id);
}
