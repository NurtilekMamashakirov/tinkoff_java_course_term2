package edu.java.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LinkResponse(
    @JsonProperty("id")
    Integer id,
    @JsonProperty("url")
    String url
) {
}
