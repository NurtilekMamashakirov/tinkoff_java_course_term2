package edu.java.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.dto.response.StackOverflowResponse;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StackOverflowClientTest {

    private WireMockServer wireMockServer;
    private final static int PORT = 8088;

    @BeforeEach
    public void startWiremock() {
        wireMockServer = new WireMockServer(PORT);
        wireMockServer.start();
    }

    @AfterEach
    public void stopWiremock() {
        wireMockServer.stop();
    }

    @Test
    public void fetchTest() {
        StackOverflowResponse expected =
            new StackOverflowResponse(1L, "testTitle", OffsetDateTime.parse("2024-01-12T12:39:38Z"));
        wireMockServer.stubFor(get(urlEqualTo("/test"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"question_id\": \"1\"," + "\n"
                    + "\"title\": \"testTitle\"," + "\n"
                    + "\"last_activity_date\": \"2024-01-12T12:39:38Z\"}")));
        StackOverflowClient client = new StackOverflowClient("http://localhost:8088");
        StackOverflowResponse actual = client.fetch("/test");
        assertThat(expected).isEqualTo(actual);
    }

}
