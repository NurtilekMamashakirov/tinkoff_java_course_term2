package edu.java.bot.dataBase;

import com.pengrad.telegrambot.model.User;
import java.util.HashMap;

public class UsersDataBase { // imitation of data base

    private UsersDataBase() {
    }

    private final static HashMap<Long, User> USERS = new HashMap<>();

    public static void addUser(User user) {
        USERS.put(user.id(), user);
    }

    public static void removeUser(User user) {
        USERS.remove(user.id());
    }

    public static User getUserById(Long id) {
        return USERS.get(id);
    }

}
