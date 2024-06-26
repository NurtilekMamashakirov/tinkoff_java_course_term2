package edu.java.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ApiErrorResponse(
    @JsonProperty("description")
    String description,
    @JsonProperty("code")
    String code,
    @JsonProperty("exceptionName")
    String exceptionName,
    @JsonProperty("exceptionMessage")
    String exceptionMessage,
    @JsonProperty("stackTrace")
    List<String> stackTrace
) {
}
