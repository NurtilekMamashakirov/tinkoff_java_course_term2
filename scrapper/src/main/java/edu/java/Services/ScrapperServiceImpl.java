package edu.java.Services;

import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import org.springframework.stereotype.Service;

@Service
public class ScrapperServiceImpl implements ScrapperService {



    @Override
    public ListLinksResponse getLinks(Long chatId) {
        return null;
    }

    @Override
    public LinkResponse deleteLink(Long chatId, String link) {
        return null;
    }

    @Override
    public void deleteChat(Long id) {

    }

    @Override
    public void addChat(Long id) {

    }

    @Override
    public LinkResponse addLink(Long chatId, String link) {
        return null;
    }
}
