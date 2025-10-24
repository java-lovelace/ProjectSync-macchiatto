package com.crudactivity.projectsync.controllers;

import com.crudactivity.projectsync.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message",exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleNotFound(BadRequestException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",exception.getMessage()));
    }

    //MethodArgumentNotValidException -> manage @valid errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleNotFound(MethodArgumentNotValidException exception){
        String errors = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Validation error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message",exception.getMessage()));
    }
}
