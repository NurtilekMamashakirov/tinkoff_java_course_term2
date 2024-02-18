package edu.java.bot.dataBase;

import com.pengrad.telegrambot.model.User;
import java.util.HashMap;

public class UsersDataBase { // imitation of data base

    private final static HashMap<Long, User> users = new HashMap<>();

    public static void addUser(User user) {
        users.put(user.id(), user);
    }

    public static void removeUser(User user) {
        users.remove(user.id());
    }

    public static User getUserById(Long id) {
        return users.get(id);
    }

}
