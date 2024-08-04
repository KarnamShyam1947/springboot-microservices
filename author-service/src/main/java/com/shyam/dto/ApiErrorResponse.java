package com.shyam.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class ApiErrorResponse {
    private String path;    
    private int statusCode;
    private String message;    
    private LocalDateTime datetime;
    private Map<String, Object> errors;

    public ApiErrorResponse() {
        this.datetime = LocalDateTime.now(); 
    }
}
