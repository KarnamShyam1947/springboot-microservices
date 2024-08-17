package com.shyam.controllers;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shyam.dto.ApiErrorResponse;
import com.shyam.exceptions.InvalidUserDetailsException;
import com.shyam.exceptions.UserEmailNotVerifiedException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpServletRequest httpServletRequest;
    private final DateTimeFormatter formatter;

    @ExceptionHandler(value = InvalidUserDetailsException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidUserDetailsException(InvalidUserDetailsException ex) {
        
        ApiErrorResponse response = new ApiErrorResponse();

        response.setMessage(ex.getMessage());
        response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        response.setPath(httpServletRequest.getServletPath());
        response.setTimestamp(formatter.format(LocalDateTime.now()));

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(value = UserEmailNotVerifiedException.class)
    public ResponseEntity<ApiErrorResponse> handleUserEmailNotVerifiedException(UserEmailNotVerifiedException ex) {
        
        ApiErrorResponse response = new ApiErrorResponse();

        response.setMessage(ex.getMessage());
        response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        response.setPath(httpServletRequest.getServletPath());
        response.setTimestamp(formatter.format(LocalDateTime.now()));

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        
        ApiErrorResponse response = new ApiErrorResponse();

        response.setMessage(ex.getMessage());
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setPath(httpServletRequest.getServletPath());
        response.setTimestamp(formatter.format(LocalDateTime.now()));

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
    
}
