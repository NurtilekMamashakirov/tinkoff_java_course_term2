package edu.java.bot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

public record LinkResponse(
    @JsonProperty("id")
    Integer id,
    @JsonProperty("url")
    URI url
) {
}
