package edu.java.clients.bot;

import edu.java.dto.request.LinkUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.reactive.function.client.WebClient;

public class BotClient {

    @Value(value = "${app.bot.baseUrl}")
    private String baseUrl = "http://localhost:8090";
    @Value(value = "${app.bot.updatesUri}")
    private String updatesUri = "/updates";
    private RetryTemplate retryTemplate;
    private WebClient webClient;

    public BotClient(RetryTemplate retryTemplate) {
        webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
        this.retryTemplate = retryTemplate;
    }

    public BotClient(String baseUrl, RetryTemplate retryTemplate) {
        webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
        this.retryTemplate = retryTemplate;
    }

    public void fetch(LinkUpdate linkUpdate) {
        retryTemplate.execute(context -> webClient
            .post()
            .uri(updatesUri)
            .bodyValue(linkUpdate)
            .retrieve()
            .bodyToMono(BotClient.class)
            .block());
    }

}
