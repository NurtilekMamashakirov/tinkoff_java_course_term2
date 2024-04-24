package edu.java.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.List;

public record LinkUpdate(
    @JsonProperty("id")
    Integer id,
    @JsonProperty("url")
    URI url,
    @JsonProperty("description")
    String description,
    @JsonProperty("tgChatIds")
    List<Integer> tgChatIds
) {
}
