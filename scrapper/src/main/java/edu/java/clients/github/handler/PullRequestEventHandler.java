package edu.java.clients.github.handler;

import edu.java.clients.github.dto.GitHubEventResponse;
import org.springframework.stereotype.Component;

@Component
public class PullRequestEventHandler implements EventHandler {
    private final static String MESSAGE = "В репозитории новый Pull Request.\n";

    @Override
    public String handle(GitHubEventResponse event) {
        return MESSAGE;
    }

    @Override
    public boolean supports(GitHubEventResponse event) {
        return event.type().equals("PullRequestEvent");
    }
}
