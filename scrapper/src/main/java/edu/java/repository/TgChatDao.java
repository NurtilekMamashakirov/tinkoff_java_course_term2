package edu.java.repository;

import edu.java.dto.models.Chat;
import java.util.List;

public interface TgChatDao {
    boolean deleteChat(Long id);

    boolean addChat(Long id);

    List<Chat> getChatsByLink(Long linkId);

    boolean chatExist(Long id);
}
