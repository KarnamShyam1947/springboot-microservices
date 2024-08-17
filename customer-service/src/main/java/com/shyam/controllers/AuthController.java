package com.shyam.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.CustomerDTO;
import com.shyam.dto.CustomerResponse;
import com.shyam.dto.LoginRequest;
import com.shyam.dto.LoginResponse;
import com.shyam.exceptions.InvalidUserDetailsException;
import com.shyam.exceptions.UserEmailNotVerifiedException;
import com.shyam.services.KeyCloakService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final KeyCloakService authService;
    
    @PostMapping("/register")
    public CustomerResponse registerUser(@RequestBody CustomerDTO dto) {
        return authService.addUser(dto);
    }
    
    @GetMapping("/login")
    public LoginResponse login(
        @RequestBody LoginRequest dto
    ) throws InvalidUserDetailsException, UserEmailNotVerifiedException {
        return authService.loginUser(dto);
    }

}
