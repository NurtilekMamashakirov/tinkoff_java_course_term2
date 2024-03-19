package edu.java.clients;

import edu.java.dto.response.StackOverflowResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClient {

    @Value(value = "${api.stackOverflow.baseUrl}")
    private String baseUrl;
    private WebClient webClient;

    public StackOverflowClient() {
        webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    public StackOverflowClient(String url) {
        webClient = WebClient
            .builder()
            .baseUrl(url)
            .build();
    }

    public StackOverflowResponse fetch(String uri) {
        return webClient
            .get()
            .uri(uri)
            .retrieve()
            .bodyToMono(StackOverflowResponse.class)
            .block();
    }
}
