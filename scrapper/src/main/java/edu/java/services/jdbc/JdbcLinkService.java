package edu.java.services.jdbc;

import edu.java.dto.models.Link;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.services.LinkService;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JdbcLinkService implements LinkService {

    private JdbcLinkRepository linksDao;

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
