package com.shyam.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shyam.dto.responses.ApiResponse;
import com.shyam.exceptions.InvalidUserDetailsException;
import com.shyam.exceptions.UserAlreadyExistsException;
import com.shyam.exceptions.UserNotFoundException;
import com.shyam.exceptions.UserNotVerifiedException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    
    private final HttpServletRequest request;
    private final DateTimeFormatter formatter;

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiResponse response = ApiResponse
                                .builder()
                                .message(ex.getMessage())
                                .path(request.getServletPath())
                                .status(HttpStatus.CONFLICT.value())
                                .timestamp(formatter.format(LocalDateTime.now()))
                                .build();

        return ResponseEntity
                .status(response.getStatus())
                .body(response);

    }

    @ExceptionHandler(value = InvalidUserDetailsException.class)
    public ResponseEntity<ApiResponse> handleInvalidUserDetailsException(InvalidUserDetailsException ex) {
        ApiResponse response = ApiResponse
                                .builder()
                                .message(ex.getMessage())
                                .path(request.getServletPath())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .timestamp(formatter.format(LocalDateTime.now()))
                                .build();

        return ResponseEntity
                .status(response.getStatus())
                .body(response);

    }
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ApiResponse response = ApiResponse
                                .builder()
                                .message(ex.getMessage())
                                .path(request.getServletPath())
                                .status(HttpStatus.NOT_FOUND.value())
                                .timestamp(formatter.format(LocalDateTime.now()))
                                .build();

        return ResponseEntity
                .status(response.getStatus())
                .body(response);

    }
    @ExceptionHandler(value = UserNotVerifiedException.class)
    public ResponseEntity<ApiResponse> handleUserNotVerifiedException(UserNotVerifiedException ex) {
        ApiResponse response = ApiResponse
                                .builder()
                                .message(ex.getMessage())
                                .path(request.getServletPath())
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .timestamp(formatter.format(LocalDateTime.now()))
                                .build();

        return ResponseEntity
                .status(response.getStatus())
                .body(response);

    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        ApiResponse response = ApiResponse
                                .builder()
                                .message(ex.getMessage())
                                .path(request.getServletPath())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .timestamp(formatter.format(LocalDateTime.now()))
                                .build();

        return ResponseEntity
                .status(response.getStatus())
                .body(response);

    }
}
