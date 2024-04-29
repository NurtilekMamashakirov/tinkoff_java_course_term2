package edu.java.clients.bot;

import edu.java.dto.request.LinkUpdate;
import org.springframework.web.reactive.function.client.WebClient;

public class BotClient {
    private String baseUrl;
    private String updatesUri;
    private WebClient webClient;

    public BotClient(String baseUrl, String updatesUri) {
        this.baseUrl = baseUrl;
        this.updatesUri = updatesUri;
        webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    public void fetch(LinkUpdate linkUpdate) {
        webClient
            .post()
            .uri(updatesUri)
            .bodyValue(linkUpdate)
            .retrieve()
            .bodyToMono(BotClient.class)
            .block();
    }

}
