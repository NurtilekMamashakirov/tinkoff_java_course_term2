package edu.java.clients.github.handler;

import edu.java.clients.github.dto.GitHubEventResponse;
import org.springframework.stereotype.Component;

@Component
public class PushEventHandler implements EventHandler {
    private final static String MESSAGE = "В репозитории новый пуш.\n";

    @Override
    public String handle(GitHubEventResponse event) {
        return MESSAGE;
    }

    @Override
    public boolean supports(GitHubEventResponse event) {
        return event.type().equals("PushEvent");
    }
}
