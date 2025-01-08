package com.shyam.services;

import java.util.List;

import org.keycloak.OAuth2Constants;
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

import com.shyam.dto.requests.LoginRequest;
import com.shyam.dto.requests.UserRequest;
import com.shyam.dto.responses.LoginResponse;
import com.shyam.dto.responses.UserResponse;
import com.shyam.exceptions.InvalidUserDetailsException;
import com.shyam.exceptions.UserAlreadyExistsException;
import com.shyam.exceptions.UserNotFoundException;
import com.shyam.exceptions.UserNotVerifiedException;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakAuthService {

    private final KeycloakRolesService roleService;
    private final RestTemplate restTemplate;
    private final Keycloak keycloak;   

    @Value("${keycloak.realm}")
    private String realm;
    
    @Value("${keycloak.client.client-id}")
    private String keycloakClientId;
    
    private UsersResource getUsersResource(){
        return keycloak.realm(realm).users();
    }
    
    public UserResource getUserById(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    public UserRepresentation getUserByEmail(String email) throws UserNotFoundException {
        try {
            UsersResource usersResource = getUsersResource();
            List<UserRepresentation> userRepresentations = usersResource.searchByEmail(email, true);
            return userRepresentations.get(0);
        } 
        catch (IndexOutOfBoundsException e) {
            throw new UserNotFoundException("Invalid email");
        }
    }
    
    public UserRepresentation getUserByUsername(String username) throws UserNotFoundException {
        try {
            UsersResource usersResource = getUsersResource();
            List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
            return userRepresentations.get(0);
        } 
        catch (IndexOutOfBoundsException e) {
            throw new UserNotFoundException("Invalid username");
        }
    }

    public void sendVerificationEmail(String userId) {
        log.info("Sending email for verification to user id : {}", userId);
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();

    }

    public UserResponse addUser(UserRequest request) throws UserAlreadyExistsException {
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

        UserResponse userResponse = new UserResponse();
        System.out.println(response.getStatus());
        System.out.println(String.format("%s", response.getStatusInfo()));

        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);

            roleService.assignClientRole(userId, "client_user");

            userResponse.setUserId(userId);
            userResponse.setStatusCode(response.getStatus());
            userResponse.setMessage("User created successfully");
            userResponse.setStatus(String.format("%s", response.getStatusInfo()));

            return userResponse;
        }

        else if (response.getStatus() == 409) {
            throw new UserAlreadyExistsException("Already an user exists with same email address");
        }

        return null;
    }

    public LoginResponse loginUser(
        LoginRequest loginRequest
    ) throws InvalidUserDetailsException, 
                UserNotVerifiedException, 
                UserNotFoundException {

        UserRepresentation user = getUserByUsername(loginRequest.getUsername());
        
        if (!user.isEmailVerified()) {
            sendVerificationEmail(user.getId());
            
            System.out.println(user.getId());
            throw new UserNotVerifiedException(
                String.format(
                    "User email not verified. A verification email send to %s mail id. please check", 
                    user.getEmail()
                )
            );
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", keycloakClientId);
            body.add("grant_type", OAuth2Constants.PASSWORD);
            body.add("password", loginRequest.getPassword());
            body.add("username", loginRequest.getUsername());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<LoginResponse> requestEntity = restTemplate.postForEntity("/token", entity, LoginResponse.class);

            return requestEntity.getBody();
        }
        catch(Exception e) {
            throw new InvalidUserDetailsException("Invalid user credentials provided");
        }
    }
    
    public void sendForgotPasswordLink(String email) throws UserNotFoundException {
        UserRepresentation userRepresentation = getUserByEmail(email);
        String userId = userRepresentation.getId();

        UserResource user = getUserById(userId);
        user.executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

}