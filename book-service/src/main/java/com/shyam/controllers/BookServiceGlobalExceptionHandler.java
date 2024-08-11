package com.shyam.controllers;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shyam.dto.response.ApiErrorResponse;
import com.shyam.exceptions.AuthorNotFoundException;
import com.shyam.exceptions.BookNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class BookServiceGlobalExceptionHandler {

    private final HttpServletRequest request;
    private final DateTimeFormatter formatter;

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleBookNotFoundException(BookNotFoundException e) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(formatter.format(LocalDateTime.now()));

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(value = AuthorNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthorNotFoundException(AuthorNotFoundException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getServletPath());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimestamp(formatter.format(LocalDateTime.now()));
        
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }
    
    @ExceptionHandler(value = AuthorizationDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getServletPath());
        errorResponse.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorResponse.setTimestamp(formatter.format(LocalDateTime.now()));
        
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }

     @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getServletPath());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(formatter.format(LocalDateTime.now()));

        Map<String, Object> errors = e.getAllErrors()
                                        .stream()
                                        .collect(Collectors.toMap(
                                            error -> ((FieldError)error).getField(), 
                                            ObjectError::getDefaultMessage
                                        ));

        errorResponse.setErrors(errors);
        
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(RuntimeException e) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setTimestamp(formatter.format(LocalDateTime.now()));

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
