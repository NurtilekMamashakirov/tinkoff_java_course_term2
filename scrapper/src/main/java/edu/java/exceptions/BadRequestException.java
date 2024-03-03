package edu.java.exceptions;

import lombok.Data;

@Data
public class BadRequestException extends RuntimeException {
    private String description;

    public BadRequestException(String message, String description) {
        super(message);
        this.description = description;
    }
}
