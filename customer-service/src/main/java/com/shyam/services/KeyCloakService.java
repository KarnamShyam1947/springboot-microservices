package com.shyam.services;

import java.util.List;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.shyam.dto.CustomerDTO;
import com.shyam.dto.CustomerResponse;
import com.shyam.dto.LoginRequest;
import com.shyam.dto.LoginResponse;
import com.shyam.exceptions.InvalidUserDetailsException;
import com.shyam.exceptions.UserEmailNotVerifiedException;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeyCloakService {

    private final RestTemplate restTemplate;

    @Value("${keycloak.realm}")
    private String realm;
    
    @Value("${keycloak.client.client-id}")
    private String keycloakClientId;

    private final Keycloak keycloak;   
    
    public CustomerResponse addUser(CustomerDTO request) {
        UserRepresentation  userRepresentation= new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(request.getFirstName());
        userRepresentation.setLastName(request.getLastName());
        userRepresentation.setUsername(request.getUserName());
        userRepresentation.setEmail(request.getEmail());
        userRepresentation.setEmailVerified(false);
        
        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(request.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(List.of(credentialRepresentation));
        UsersResource user = getUsersResource();
        Response response = user.create(userRepresentation);
        
        // TODO: raise correct user exception
        CustomerResponse customerResponse = new CustomerResponse();
        if (response.getStatus() >= 400) {
            String userId = CreatedResponseUtil.getCreatedId(response);

            customerResponse.setUserId(userId);
            customerResponse.setStatusCode(response.getStatus());
            customerResponse.setMessage("User created successfully");
            customerResponse.setStatus(String.format("%s", response.getStatusInfo()));

            return customerResponse;
        }

        customerResponse.setStatusCode(response.getStatus());
        customerResponse.setMessage("Failed to create user");
        customerResponse.setStatus(String.format("%s", response.getStatusInfo()));

        return customerResponse;
    }

    private UsersResource getUsersResource(){
        return keycloak.realm(realm).users();
    }

     public UserResource getUser(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    public UserRepresentation getUserByEmail(String email) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(email, true);
        
        return userRepresentations.get(0);
    }
    
    public UserRepresentation getUserByUsername(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
        
        return userRepresentations.get(0);
    }

    public void sendVerificationEmail(String userId) {

        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
        
    }

    public LoginResponse loginUser(LoginRequest loginRequest) throws InvalidUserDetailsException, UserEmailNotVerifiedException {

        
        
        try {
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", keycloakClientId);
            body.add("grant_type", "password");
            body.add("password", loginRequest.getPassword());
            body.add("username", loginRequest.getUsername());
            
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<LoginResponse> requestEntity = restTemplate.postForEntity("/token", entity, LoginResponse.class);

            UserRepresentation user = getUserByUsername(loginRequest.getUsername());
            if (user.isEmailVerified()) {
                sendVerificationEmail(user.getId());
                throw new UserEmailNotVerifiedException();
            }

            return requestEntity.getBody();
        }
        catch(UserEmailNotVerifiedException e) {
            throw new UserEmailNotVerifiedException("User Email not verified");
        }
        catch(Exception e) {
            throw new InvalidUserDetailsException();
        }
    }
    
}