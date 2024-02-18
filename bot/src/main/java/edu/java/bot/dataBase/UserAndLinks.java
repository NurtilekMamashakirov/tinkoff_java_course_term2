package edu.java.bot.dataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserAndLinks { //imitation of data base
    private final static HashMap<Long, List<String>> usersAndLinks = new HashMap<>();

    public static void addLinkToUser(Long userId, String url) {
        if (usersAndLinks.get(userId) == null) {
            List<String> links = new ArrayList<>();
            links.add(url);
            usersAndLinks.put(userId, links);
        } else {
            List<String> links = usersAndLinks.get(userId);
            links.add(url);
            usersAndLinks.put(userId, links);
        }
    }

    public static Boolean deleteLinkToUser(Long userId, String url) {
        List<String> links = usersAndLinks.get(userId);
        Boolean check = links.remove(url);
        usersAndLinks.put(userId, links);
        return check;
    }
}
