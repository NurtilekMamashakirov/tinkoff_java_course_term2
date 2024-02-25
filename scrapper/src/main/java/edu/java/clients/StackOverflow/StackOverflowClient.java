package edu.java.clients.StackOverflow;

import edu.java.clients.Client;
import edu.java.clients.ResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClient<T> implements Client {

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

    @Override
    public ResponseDto<StackOverflowResponse> fetch(String uri) {
        return new ResponseDto(webClient
            .get()
            .uri(uri)
            .retrieve()
            .bodyToMono(StackOverflowResponse.class)
            .block());
    }
}
