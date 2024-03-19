package edu.java.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.dto.response.GitHubResponse;
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
public class GitHubClientTest {

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
        GitHubResponse expected = new GitHubResponse(1L, "testName", OffsetDateTime.parse("2024-01-12T12:39:38Z"));
        wireMockServer.stubFor(get(urlEqualTo("/test"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"id\": \"1\"," + "\n"
                    + "\"full_name\": \"testName\"," + "\n"
                    + "\"updated_at\": \"2024-01-12T12:39:38Z\"}")));
        GitHubClient gitHubClient = new GitHubClient("http://localhost:8088");
        GitHubResponse actual = gitHubClient.fetch("/test");
        assertThat(expected).isEqualTo(actual);
    }
}
