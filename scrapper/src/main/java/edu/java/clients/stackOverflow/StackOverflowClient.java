package edu.java.clients.stackOverflow;

import edu.java.clients.stackOverflow.dto.StackOverflowResponse;
import java.net.URI;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class StackOverflowClient {
    private String baseUrl;
    private WebClient webClient;
    private final static String STACK_OVERFLOW_HOST = "stackoverflow.com";
    private final static Pattern PATH_PATTERN = Pattern.compile("^/questions/(.*)/(.*)");

    public StackOverflowClient(String url) {
        baseUrl = url;
        webClient = WebClient
            .builder()
            .baseUrl(url)
            .exchangeStrategies(ExchangeStrategies
                .builder()
                .codecs(codecs -> codecs
                    .defaultCodecs()
                    .maxInMemorySize(500 * 1024))
                .build())
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
