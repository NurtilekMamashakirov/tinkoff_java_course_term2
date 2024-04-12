package edu.java.services.jpa;

import edu.java.dto.entity.ChatEntity;
import edu.java.dto.entity.LinkEntity;
import edu.java.dto.models.Link;
import edu.java.exceptions.UsersException;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.repository.jpa.JpaTgChatRepository;
import edu.java.services.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaLinkService implements LinkService {
    private JpaLinkRepository linkRepository;
    private JpaTgChatRepository chatRepository;
    private static final String EXCEPTION_MESSAGE = "Chat is not registered";
    private static final String EXCEPTION_DESCRIPTION = "No such chat";

    @Override
    public Link add(long tgChatId, URI url) {
        ChatEntity chat = chatRepository
            .findById(tgChatId)
            .orElseThrow(() -> new UsersException(EXCEPTION_MESSAGE, EXCEPTION_DESCRIPTION));
        LinkEntity link = getOrCreateLink(url);
        chat.addLink(link);
        chatRepository.save(chat);
        return new Link(
            link.getId(),
            link.getUrl(),
            link.getUpdatedAt(),
            link.getCheckedAt(),
            List.of()
        );
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        ChatEntity chat = chatRepository
            .findById(tgChatId)
            .orElseThrow(() -> new UsersException(EXCEPTION_MESSAGE, EXCEPTION_DESCRIPTION));
        Boolean chatHasLink = chat
            .getLinks()
            .stream()
            .anyMatch(link -> link.getUrl().equals(url));
        if (!chatHasLink) {
            return null;
        }
        LinkEntity linkToRemove = chat
            .getLinks()
            .stream()
            .filter(link -> link.getUrl().equals(url))
            .findFirst()
            .get();
        Set<LinkEntity> linksOfChat = chat.getLinks();
        linksOfChat.remove(linkToRemove);
        chat.setLinks(linksOfChat);
        return new Link(
            linkToRemove.getId(),
            linkToRemove.getUrl(),
            linkToRemove.getUpdatedAt(),
            linkToRemove.getCheckedAt(),
            List.of()
        );
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        ChatEntity chat = chatRepository
            .findById(tgChatId)
            .orElseThrow(() -> new UsersException(EXCEPTION_MESSAGE, EXCEPTION_DESCRIPTION));
        Set<LinkEntity> setOfLinkEntity = chat.getLinks();
        ArrayList<Link> listOfLinks = new ArrayList<>();
        for (LinkEntity linkEntity : setOfLinkEntity) {
            Link link = new Link(
                linkEntity.getId(),
                linkEntity.getUrl(),
                linkEntity.getUpdatedAt(),
                linkEntity.getCheckedAt(),
                List.of()
            );
            listOfLinks.add(link);
        }
        return listOfLinks;
    }

    private LinkEntity getOrCreateLink(URI url) {
        Optional<LinkEntity> optionalLink = linkRepository.findByUrl(url);
        if (optionalLink.isEmpty()) {
            LinkEntity link = new LinkEntity();
            link.setUrl(url);
            link.setCheckedAt(OffsetDateTime.now());
            link.setUpdatedAt(OffsetDateTime.now());
            link.setChats(new HashSet<>());
            linkRepository.save(link);
            return link;
        }
        return optionalLink.get();
    }
}
