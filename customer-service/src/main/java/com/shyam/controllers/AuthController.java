package com.shyam.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.requests.LoginRequest;
import com.shyam.dto.requests.UserRequest;
import com.shyam.dto.responses.LoginResponse;
import com.shyam.dto.responses.UserResponse;
import com.shyam.exceptions.InvalidUserDetailsException;
import com.shyam.exceptions.UserAlreadyExistsException;
import com.shyam.exceptions.UserNotFoundException;
import com.shyam.exceptions.UserNotVerifiedException;
import com.shyam.services.KeycloakAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KeycloakAuthService service;
    
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
        @RequestBody UserRequest request
    ) throws UserAlreadyExistsException {

        UserResponse user = service.addUser(request);
        
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(user);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @RequestBody LoginRequest request
    ) throws 
        InvalidUserDetailsException, 
        UserNotVerifiedException, 
        UserNotFoundException {

        LoginResponse user = service.loginUser(request);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(user);
    }
    
    @PutMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(
        @RequestParam(name = "email") String email
    ) throws UserNotFoundException{

        service.sendForgotPasswordLink(email);

        return ResponseEntity.ok().body(Map.of("message","mail send check your inbox"));
    }

}