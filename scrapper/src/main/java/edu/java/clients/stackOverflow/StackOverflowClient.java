package edu.java.clients.stackOverflow;

import edu.java.clients.stackOverflow.dto.StackOverflowResponse;
import java.net.URI;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClient {

    @Value(value = "${app.stackOverflow.baseUrl}")
    private String baseUrl = "https://api.stackexchange.com/2.3/";
    private WebClient webClient;
    private RetryTemplate retryTemplate;
    private final static String STACK_OVERFLOW_HOST = "stackoverflow.com";
    private final static Pattern PATH_PATTERN = Pattern.compile("^/questions/(.*)/(.*)");

    public StackOverflowClient(RetryTemplate retryTemplate) {
        webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
        this.retryTemplate = retryTemplate;
    }

    public StackOverflowClient(String url, RetryTemplate retryTemplate) {
        webClient = WebClient
            .builder()
            .baseUrl(url)
            .build();
        this.retryTemplate = retryTemplate;
    }

    public StackOverflowResponse fetch(String uri) {
        return retryTemplate.execute(context -> webClient
            .get()
            .uri(uri)
            .retrieve()
            .bodyToMono(StackOverflowResponse.class)
            .block());
    }

    public boolean isValidated(URI url) {
        return url.getHost().equalsIgnoreCase(STACK_OVERFLOW_HOST)
            && PATH_PATTERN.matcher(url.getPath()).matches();
    }
}
