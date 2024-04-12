package edu.java.bot.clients;

import edu.java.bot.dto.request.AddLinkRequest;
import edu.java.bot.dto.request.RemoveLinkRequest;
import edu.java.bot.dto.response.LinkResponse;
import edu.java.bot.dto.response.ListLinksResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

public class ScrapperClient {

    @Value("${scrapper.baseUrl}")
    private String baseUrl = "http://localhost:8080";
    private WebClient webClient;
    @Value("${scrapper.linksUri}")
    private String linksUri = "/links";
    @Value("${scrapper.chatUri}")
    private String chatUri = "/tg-chat";
    private static final String HEADER_NAME = "Tg-Chat-Id";

    public ScrapperClient() {
        webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    public ScrapperClient(String baseUrl) {
        webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    public LinkResponse addLink(Long id, String link) {
        AddLinkRequest addLinkRequest = new AddLinkRequest(link);
        return webClient
            .post()
            .uri(linksUri)
            .bodyValue(addLinkRequest)
            .header(HEADER_NAME, id.toString())
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    public LinkResponse removeLink(Long id, String link) {
        RemoveLinkRequest request = new RemoveLinkRequest(link);
        return webClient
            .method(HttpMethod.DELETE)
            .uri(linksUri)
            .bodyValue(request)
            .header(HEADER_NAME, id.toString())
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    public ListLinksResponse getLinks(Long id) {
        return webClient
            .get()
            .uri(linksUri)
            .header(HEADER_NAME, id.toString())
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    public void addChat(Long id) {
        String uri = chatUri + "/" + id.toString();
        webClient.post()
            .uri(uri)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    public void deleteChat(Long id) {
        String uri = chatUri + "/" + id.toString();
        webClient.delete()
            .uri(uri)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    public boolean checkIfChatExist(Long id) {
        String uri = chatUri + "/" + id.toString();
        return Boolean.TRUE.equals(webClient
            .get()
            .uri(uri)
            .retrieve()
            .bodyToMono(Boolean.class)
            .block());
    }

    public boolean checkIfLinkExist(Long id, String link) {
        List<LinkResponse> listLinksResponse = getLinks(id).links();
        return listLinksResponse
            .stream()
            .anyMatch(linkResponse -> linkResponse.url().toString().equalsIgnoreCase(link));
    }

}
