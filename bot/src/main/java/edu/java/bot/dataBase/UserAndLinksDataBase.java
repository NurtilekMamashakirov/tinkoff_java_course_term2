package edu.java.bot.dataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserAndLinksDataBase { //imitation of data base

    private UserAndLinksDataBase() {

    }

    private final static HashMap<Long, List<String>> USERS_AND_LINKS = new HashMap<>();

    public static void addLinkToUser(Long userId, String url) {
        if (USERS_AND_LINKS.get(userId) == null) {
            List<String> links = new ArrayList<>();
            links.add(url);
            USERS_AND_LINKS.put(userId, links);
        } else {
            List<String> links = USERS_AND_LINKS.get(userId);
            links.add(url);
            USERS_AND_LINKS.put(userId, links);
        }
    }

    public static Boolean deleteLinkToUser(Long userId, String url) {
        List<String> links = USERS_AND_LINKS.get(userId);
        Boolean check = links.remove(url);
        USERS_AND_LINKS.put(userId, links);
        return check;
    }

    public static List<String> getAllLinksById(Long userId) {
        return USERS_AND_LINKS.get(userId);
    }
}
