package edu.java.clients;

import edu.java.dto.response.GitHubResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GitHubClient {

    @Value(value = "${app.github.baseUrl}")
    private String baseUrl = "https://api.github.com/repos";

    private WebClient webClient;

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
}
