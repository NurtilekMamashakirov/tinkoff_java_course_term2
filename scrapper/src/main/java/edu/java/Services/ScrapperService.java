package edu.java.Services;

import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;

public interface ScrapperService {
    ListLinksResponse getLinks(Long chatId);

    LinkResponse deleteLink(Long chatId, String link);

    LinkResponse addLink(Long chatId, String link);

    void deleteChat(Long id);

    void addChat(Long id);
}
