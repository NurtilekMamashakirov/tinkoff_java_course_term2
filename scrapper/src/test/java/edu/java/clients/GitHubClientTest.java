package edu.java.clients;

import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.clients.GitHub.GitHubClient;
import edu.java.clients.GitHub.GitHubResponse;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GitHubClientTest {

    @Test
    public void fetchTest() {
//        WebClient webClient = WebClient.create("https://api.github.com");
//        System.out.println(webClient.get()
//            .uri("repos/NurtilekMamashakirov/java-course-2023/events")
//            .retrieve()
//            .bodyToMono(String.class)
//            .block());
        WireMock.configureFor("localhost", 8081);

        stubFor(get(urlEqualTo("/test"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{\"id\": \"1\"," + "\n"
                    + "\"full_name\": \"testName\"," + "\n"
                    + "\"updated_at\": \"2024-01-12T12:39:38Z\"}")));

        GitHubClient gitHubClient = new GitHubClient("http://localhost:8081");
        ResponseDto<GitHubResponse> response = gitHubClient.fetch("/test");
        System.out.println(response.getResponse());
//        assertThat(response.getResponse()).isEqualTo(new GitHubResponse(1, ))
    }

}
