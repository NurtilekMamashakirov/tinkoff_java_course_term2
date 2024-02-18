package edu.java.bot.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class LinkUtils {

    private static Pattern permittedURLPattern =
        Pattern.compile("^(https://https://github.com|https://stackoverflow.com).*$");

    public static Boolean checkLink(String url) {
        Matcher matcher = permittedURLPattern.matcher(url);
        if (!matcher.matches()) {
            return false;
        }
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForObject(url, ResponseEntity.class);
        int statusCode = response.getStatusCode().value();
        if (!(statusCode >= 200 && statusCode < 300)) {
            return false;
        }
        return true;
    }

}
