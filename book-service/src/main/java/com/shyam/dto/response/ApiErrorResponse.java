package com.shyam.dto.response;

import java.util.Map;

import lombok.Data;

@Data
public class ApiErrorResponse {
    private String path;    
    private int statusCode;
    private String message;    
    private String timestamp;
    private Map<String, Object> errors;

}
