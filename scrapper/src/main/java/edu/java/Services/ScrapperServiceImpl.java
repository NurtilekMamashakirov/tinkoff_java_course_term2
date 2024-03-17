package edu.java.Services;

import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.exceptions.UsersException;
import edu.java.repository.LinksDao;
import edu.java.repository.UsersDao;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScrapperServiceImpl implements ScrapperService {

    private final static String USER_EXCEPTION_MESSAGE = "User exception";
    private final static String USER_ALREADY_EXIST_DESCRIPTION = "Повторная регистрация";
    private final static String USER_DOES_NOT_EXIST_DESCRIPTION = "Пользователь не найден";

    private UsersDao usersDao;
    private LinksDao linksDao;

    @Override
    public ListLinksResponse getLinks(Long chatId) {
        if (!usersDao.chatExist(chatId)) {
            throw new UsersException(USER_EXCEPTION_MESSAGE, USER_DOES_NOT_EXIST_DESCRIPTION);
        }
        List<String> links = linksDao.getLinks(chatId);
        if (links == null) {
            return new ListLinksResponse(new ArrayList<>(), 0);
        }
        final Long finalId = chatId;
        List<LinkResponse> linkResponses = links
            .stream()
            .map(linkString -> new LinkResponse(finalId.intValue(), linkString))
            .toList();
        return new ListLinksResponse(linkResponses, linkResponses.size());
    }

    @Override
    public LinkResponse deleteLink(Long chatId, String link) {
        if (!usersDao.chatExist(chatId)) {
            throw new UsersException(USER_EXCEPTION_MESSAGE, USER_DOES_NOT_EXIST_DESCRIPTION);
        }
        linksDao.deleteLink(chatId, link);
        return new LinkResponse(
            chatId.intValue(),
            link
        );
    }

    @Override
    public void deleteChat(Long id) {
        if (!usersDao.deleteChat(id)) {
            throw new UsersException(USER_EXCEPTION_MESSAGE, USER_DOES_NOT_EXIST_DESCRIPTION);
        }
    }

    @Override
    public void addChat(Long id) {
        if (!usersDao.addChat(id)) {
            throw new UsersException(USER_EXCEPTION_MESSAGE, USER_ALREADY_EXIST_DESCRIPTION);
        }
    }

    @Override
    public LinkResponse addLink(Long chatId, String link) {
        if (!usersDao.chatExist(chatId)) {
            throw new UsersException(USER_EXCEPTION_MESSAGE, USER_DOES_NOT_EXIST_DESCRIPTION);
        }
        linksDao.addLink(chatId, link);
        return new LinkResponse(
            chatId.intValue(),
            link
        );
    }
}
