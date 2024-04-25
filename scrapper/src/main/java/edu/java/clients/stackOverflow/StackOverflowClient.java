package edu.java.clients.stackOverflow;

import edu.java.clients.stackOverflow.dto.StackOverflowResponse;
import java.net.URI;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class StackOverflowClient {

    @Value(value = "${app.stackOverflow.baseUrl}")
    private String baseUrl = "https://api.stackexchange.com/2.3/";
    private WebClient webClient;
    private final static String STACK_OVERFLOW_HOST = "stackoverflow.com";
    private final static Pattern PATH_PATTERN = Pattern.compile("^/questions/(.*)/(.*)");

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
        try {
            return webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(StackOverflowResponse.class)
                .block();
        } catch (Exception ex) {
            log.info("Произошла ошибка при запросе на StackOverflow: ", ex);
            return null;
        }
    }

    public boolean isValidated(URI url) {
        return url.getHost().equalsIgnoreCase(STACK_OVERFLOW_HOST)
            && PATH_PATTERN.matcher(url.getPath()).matches();
    }
}
