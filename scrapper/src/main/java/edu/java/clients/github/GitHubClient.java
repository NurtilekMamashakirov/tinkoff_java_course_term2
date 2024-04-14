package edu.java.clients.github;

import edu.java.clients.github.dto.GitHubEventResponse;
import edu.java.clients.github.dto.GitHubResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
@Slf4j
public class GitHubClient {

    @Value(value = "${app.github.baseUrl}")
    private String baseUrl = "https://api.github.com/repos";

    private WebClient webClient;
    private static final String GITHUB_HOST = "github.com";
    private static final Pattern PATH_PATTERN = Pattern.compile("/(.*)/(.*)");

    public GitHubClient() {
        webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    public GitHubClient(String url) {
        webClient = WebClient
            .builder()
            .baseUrl(url)
            .build();
    }

    public GitHubResponse fetch(String uri) {
        return webClient
            .get()
            .uri(uri)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .block();
    }

    public List<GitHubEventResponse> fetchEvents(String uri) {
        GitHubEventResponse[] events = webClient
            .get()
            .uri(uri + "/events")
            .retrieve()
            .bodyToMono(GitHubEventResponse[].class)
            .block();
        if (events == null) {
            return List.of();
        }
        return Arrays.stream(events)
            .toList();
    }

    public boolean isValidated(URI uri) {
        return uri.getHost().equalsIgnoreCase(GITHUB_HOST)
            && PATH_PATTERN.matcher(uri.getPath()).matches();
    }
}
