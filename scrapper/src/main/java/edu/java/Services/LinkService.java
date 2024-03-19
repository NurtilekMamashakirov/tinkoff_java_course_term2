package edu.java.Services;

import edu.java.dto.models.Link;
import java.net.URI;
import java.util.List;

public interface LinkService {
    Link add(long tgChatId, URI url);

    Link remove(long tgChatId, URI url);

    List<Link> listAll(long tgChatId);
}

