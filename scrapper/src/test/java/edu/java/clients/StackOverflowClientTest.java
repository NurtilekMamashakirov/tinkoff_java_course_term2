package edu.java.clients;

import org.springframework.boot.test.context.SpringBootTest;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StackOverflowClientTest {

//    private WireMockServer wireMockServer;
//    private final static int PORT = 8088;
//
//    @BeforeEach
//    public void startWiremock() {
//        wireMockServer = new WireMockServer(PORT);
//        wireMockServer.start();
//    }
//
//    @AfterEach
//    public void stopWiremock() {
//        wireMockServer.stop();
//    }
//
//    @Test
//    public void fetchTest() {
//        StackOverflowResponse expected =
//            new StackOverflowResponse(List.of(new StackOverflowResponse.Item(
//                "testTitle",
//                OffsetDateTime.parse("2024-01-12T12:39:38Z")
//            )));
//        wireMockServer.stubFor(get(urlEqualTo("/test"))
//            .willReturn(aResponse()
//                .withStatus(200)
//                .withHeader("Content-Type", "application/json")
//                .withBody("{\"items\":[{\"title\":\"testTitle\", \"last_activity_date\":\"2024-01-12T12:39:38Z\"}]}")));
//        StackOverflowClient client = new StackOverflowClient("http://localhost:8088");
//        StackOverflowResponse actual = client.fetch("/test");
//        assertThat(actual).isEqualTo(expected);
//    }

}
