package edu.java.clients;

import edu.java.dto.response.GitHubResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient {

    @Value(value = "${api.github.baseUrl}")
    private String baseUrl;

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
