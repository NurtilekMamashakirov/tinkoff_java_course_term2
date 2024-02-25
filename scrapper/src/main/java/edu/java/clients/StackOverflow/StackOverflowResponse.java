package edu.java.clients.StackOverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackOverflowResponse(@JsonProperty("question_id") Long questionId,
                                    @JsonProperty("title") String title,
                                    @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate) {
}
