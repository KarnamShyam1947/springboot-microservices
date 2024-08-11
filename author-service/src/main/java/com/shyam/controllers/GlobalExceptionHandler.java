package com.shyam.controllers;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shyam.dto.ApiErrorResponse;
import com.shyam.exceptions.AuthorExistsException;
import com.shyam.exceptions.AuthorNotFoundException;
import org.springframework.security.authorization.AuthorizationDeniedException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    @ExceptionHandler(value = AuthorNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthorNotFoundException(AuthorNotFoundException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getServletPath());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }
    
    @ExceptionHandler(value = AuthorExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthorExistsException(AuthorExistsException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getServletPath());
        errorResponse.setStatusCode(HttpStatus.CONFLICT.value());
        
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

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(RuntimeException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getServletPath());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }
    
}
