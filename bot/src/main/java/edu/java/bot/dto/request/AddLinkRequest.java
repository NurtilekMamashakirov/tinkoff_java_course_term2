package edu.java.bot.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddLinkRequest(
    @JsonProperty("link")
    String link
) {
}
