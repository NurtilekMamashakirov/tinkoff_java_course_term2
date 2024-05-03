package edu.java.controllers;

import edu.java.dto.response.ApiErrorResponse;
import edu.java.exceptions.BadRequestException;
import edu.java.exceptions.UsersException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    private final static String ERROR_CODE = "400";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ApiErrorResponse badRequestHandler(BadRequestException exception) {
        return new ApiErrorResponse(
            exception.getDescription(),
            ERROR_CODE,
            exception.getClass().getSimpleName(),
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsersException.class)
    public ApiErrorResponse userExceptionHandler(UsersException exception) {
        return new ApiErrorResponse(
            exception.getDescription(),
            ERROR_CODE,
            exception.getClass().getSimpleName(),
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );

    }
}
