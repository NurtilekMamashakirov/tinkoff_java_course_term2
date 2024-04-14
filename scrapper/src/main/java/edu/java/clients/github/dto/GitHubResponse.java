package edu.java.clients.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubResponse(@JsonProperty("id") Long id,
                             @JsonProperty("full_name") String fullName,
                             @JsonProperty("updated_at") OffsetDateTime updatedAt) {
}
