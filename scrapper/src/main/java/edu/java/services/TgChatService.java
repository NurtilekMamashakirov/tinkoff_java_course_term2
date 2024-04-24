package edu.java.services;

public interface TgChatService {
    boolean register(long tgChatId);

    void unregister(long tgChatId);

    boolean checkIfChatExist(long tgChatId);
}
