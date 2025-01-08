package com.shyam.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.requests.RoleRequest;
import com.shyam.services.KeycloakRolesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RolesController {

    private final KeycloakRolesService service;

    @PutMapping("/realm")
    public ResponseEntity<Map<String, String>> assignRealmRole(@RequestBody RoleRequest request) {
        service.assignRealmRole(request.getUserId(), request.getRoleName());

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "Role assigned successfully"));
    }
    
    @PutMapping("/client")
    public ResponseEntity<Map<String, String>> assignClientRole(@RequestBody RoleRequest request) {
        service.assignRealmRole(request.getUserId(), request.getRoleName());

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "Role assigned successfully"));
    }
    
    @DeleteMapping("/realm")
    public ResponseEntity<Map<String, String>> removeRealmRole(@RequestBody RoleRequest request) {
        service.removeRealmRole(request.getUserId(), request.getRoleName());

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "role removed successfully"));

    }
    
    @DeleteMapping("/client")
    public ResponseEntity<Map<String, String>> removeClientRole(@RequestBody RoleRequest request) {
        service.removeClientRole(request.getUserId(), request.getRoleName());

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "role removed successfully"));
    }
}
