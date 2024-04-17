package edu.java.services.jooq;

import edu.java.dto.models.Link;
import edu.java.repository.jooq.impls.JooqLinkRepository;
import edu.java.services.LinkService;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JooqLinkService implements LinkService {
    private JooqLinkRepository linkRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        return linkRepository.addLink(tgChatId, url.toString());
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        return linkRepository.deleteLink(tgChatId, url.toString());
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        return linkRepository.getLinks(tgChatId);
    }
}
