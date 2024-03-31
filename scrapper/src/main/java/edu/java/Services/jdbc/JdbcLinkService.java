package edu.java.Services.jdbc;

import edu.java.Services.LinkService;
import edu.java.dto.models.Link;
import edu.java.repository.jdbc.JdbcLinksDao;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JdbcLinkService implements LinkService {

    private JdbcLinksDao linksDao;

    @Override
    public Link add(long tgChatId, URI url) {
        return linksDao.addLink(tgChatId, url.toString());
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        return linksDao.deleteLink(tgChatId, url.toString());
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        return linksDao.getLinks(tgChatId);
    }


}
