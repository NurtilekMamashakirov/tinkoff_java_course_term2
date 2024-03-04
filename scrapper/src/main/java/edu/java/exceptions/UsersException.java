package edu.java.exceptions;

import lombok.Getter;

@Getter
public class UsersException extends RuntimeException {

    private String description;

    public UsersException(String message, String description) {
        super(message);
        this.description = description;
    }
}
