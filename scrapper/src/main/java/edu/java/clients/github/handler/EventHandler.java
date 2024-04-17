package edu.java.clients.github.handler;

import edu.java.clients.github.dto.GitHubEventResponse;

public interface EventHandler {
    String handle(GitHubEventResponse event);

    boolean supports(GitHubEventResponse event);
}
