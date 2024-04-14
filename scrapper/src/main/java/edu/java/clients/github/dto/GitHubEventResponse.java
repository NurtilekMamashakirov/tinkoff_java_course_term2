package edu.java.clients.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubEventResponse(
    @JsonProperty("type") String type,
    @JsonProperty("created_at")OffsetDateTime updatedAt
    ) {
}
