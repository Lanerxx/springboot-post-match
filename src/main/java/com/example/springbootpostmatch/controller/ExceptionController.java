package com.example.springbootpostmatch.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class ExceptionController {
    /**
     * The exception of attribute validation failed
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handleValidException(MethodArgumentNotValidException exception) {
        StringBuilder strBuilder = new StringBuilder();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(e -> {
                    strBuilder.append(e.getField());
                    strBuilder.append(": ");
                    strBuilder.append(e.getDefaultMessage());
                    strBuilder.append("; ");
                });
        return Map.of("message", strBuilder.toString());
    }


    /**
     * The exception of request type conversion failed
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request) {
        String msg = request.getRequestURI() +
                ": " + "There was an error with the request address parameter \'" + exception.getValue() + "\'";
        return Map.of("message", msg);
    }

    /**
     * The exception of jackson json type conversion failed
     * @param exception
     * @return
     */
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handleInvalidFormatException(InvalidFormatException exception) {
        StringBuilder strBuilder = new StringBuilder();
        exception.getPath()
                .forEach(p -> {
                    strBuilder.append("Attribute");
                    strBuilder.append(p.getFieldName());
                    strBuilder.append("The value \'" + exception.getValue());
                    strBuilder.append("\' you entered has the type error");
                });
        return Map.of("message", strBuilder.toString());
    }


    /**
     * The exception of method - level parameter validation failed
     * @param exception
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handleConstraintViolationException(ConstraintViolationException exception) {
        StringBuilder strBuilder = new StringBuilder();
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        violations.forEach(v -> {
            strBuilder.append(v.getMessage() + "; ");
        });
        return Map.of("message", strBuilder.toString());
    }

}
