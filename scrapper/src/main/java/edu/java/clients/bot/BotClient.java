package edu.java.clients.bot;

import edu.java.dto.request.LinkUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BotClient {

    //тут почему-то @value перестал работать, пока не знаю как пофиксить. Идея этот конфиг видит через ctrl,
    // но значение не внедряется. Пока пусть будет так
    @Value(value = "${app.bot.baseUrl}")
    private String baseUrl = "http://localhost:8090";
    @Value(value = "${app.bot.updatesUri}")
    private String updatesUri = "/updates";
    private WebClient webClient;

    public BotClient() {
        webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    public BotClient(String baseUrl) {
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
