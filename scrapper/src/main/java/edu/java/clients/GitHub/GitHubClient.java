package edu.java.clients.GitHub;

import edu.java.clients.Client;
import edu.java.clients.ResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient implements Client {

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

    @Override
    public ResponseDto<GitHubResponse> fetch(String uri) {
        return new ResponseDto<>(webClient
            .get()
            .uri(uri)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .block());
    }
}
