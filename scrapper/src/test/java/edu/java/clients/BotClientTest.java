package edu.java.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.clients.bot.BotClient;
import edu.java.dto.request.LinkUpdate;
import edu.java.retry.RetryTemplateFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.retry.support.RetryTemplate;
import java.net.URI;
import java.util.List;
import java.util.Set;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class BotClientTest {
    private WireMockServer wireMockServer;
    private BotClient botClient;
    private RetryTemplate retryTemplate;

    @BeforeEach
    void init() {
        retryTemplate = RetryTemplateFactory.constant(Set.of(400), 3, 10L);
        wireMockServer = new WireMockServer(8088);
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        botClient = new BotClient("http://localhost:" + wireMockServer.port(), retryTemplate);
    }

    @AfterEach
    void stopServer() {
        wireMockServer.stop();
    }

    @Test
    void fetchTest() {
        stubFor(post(urlEqualTo("/updates")).willReturn(aResponse().withStatus(200)));
        LinkUpdate linkUpdate = new LinkUpdate(
            1,
            URI.create("https://editor.swagger.io"),
            "description",
            List.of(1, 2, 3)
        );
        botClient.fetch(linkUpdate);
    }

}
