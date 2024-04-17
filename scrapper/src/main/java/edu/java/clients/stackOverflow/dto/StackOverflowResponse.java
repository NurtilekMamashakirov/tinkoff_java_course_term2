package edu.java.clients.stackOverflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowResponse(@JsonProperty("items") List<Item> items) {
    public record Item(@JsonProperty("title") String title,
                @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate) {
    }
}
