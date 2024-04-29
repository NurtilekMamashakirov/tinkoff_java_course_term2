package edu.java.clients.github;

import edu.java.clients.github.dto.GitHubEventResponse;
import edu.java.clients.github.dto.GitHubResponse;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class GitHubClient {
    private String baseUrl;
    private WebClient webClient;
    private static final String GITHUB_HOST = "github.com";
    private static final Pattern PATH_PATTERN = Pattern.compile("/(.*)/(.*)");
    private static final String EX_MESSAGE = "Произошла ошибка при запросе на Github: ";

    public GitHubClient(String url) {
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

    public GitHubResponse fetch(String uri) {
        try {
            return webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(GitHubResponse.class)
                .block();
        } catch (Exception ex) {
            log.error(EX_MESSAGE, ex);
            return null;
        }
    }

    public List<GitHubEventResponse> fetchEvents(String uri) {
        try {
            GitHubEventResponse[] events = webClient
                .get()
                .uri("/repos" + uri + "/events")
                .retrieve()
                .bodyToMono(GitHubEventResponse[].class)
                .block();
            if (events == null) {
                return List.of();
            }
            return Arrays.stream(events)
                .toList();
        } catch (Exception ex) {
            log.info(EX_MESSAGE, ex);
            return List.of();
        }
    }

    public boolean isValidated(URI uri) {
        return uri.getHost().equalsIgnoreCase(GITHUB_HOST)
            && PATH_PATTERN.matcher(uri.getPath()).matches();
    }
}
