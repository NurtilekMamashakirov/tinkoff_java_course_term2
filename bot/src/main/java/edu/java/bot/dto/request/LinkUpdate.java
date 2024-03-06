package edu.java.bot.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.net.URI;

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
