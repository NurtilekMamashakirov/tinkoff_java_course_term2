package edu.java.bot.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RemoveLinkRequest(
    @JsonProperty("link")
    String link
) {
}
