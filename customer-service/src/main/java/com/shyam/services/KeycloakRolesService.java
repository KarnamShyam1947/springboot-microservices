package com.shyam.services;

import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakRolesService {
    
    private final Keycloak keycloak;   

    @Value("${keycloak.realm}")
    private String realm;
    
    @Value("${keycloak.client.client-id}")
    private String keycloakClientId;
    
    private UsersResource getUsersResource(){
        return keycloak.realm(realm).users();
    }
    
    private RealmResource getRealmResource(){
        return keycloak.realm(realm);
    }

    public RoleRepresentation getRealmRole(String roleName) {
        return getRealmResource().roles()
                .get(roleName)
                .toRepresentation();
    }

    public void assignRealmRole(String userId, String roleName){
        UserResource userById = getUserById(userId);
        userById
            .roles()
            .realmLevel()
            .add(List.of(getRealmRole(roleName)));
    }
    
    public void removeRealmRole(String userId, String roleName){
        UserResource userById = getUserById(userId);
        userById
            .roles()
            .realmLevel()
            .remove(List.of(getRealmRole(roleName)));
    }

    private ClientRepresentation getClientResource(){
        return keycloak
                .realm(realm)
                .clients()
                .findByClientId(keycloakClientId)
                .get(0);
    }

    public RoleRepresentation getClientRole(String roleName){
        return getRealmResource()
                .clients()
                .get(getClientResource().getId())
                .roles()
                .get(roleName)
                .toRepresentation();
    }

    public void assignClientRole(String userId, String roleName) {
        UserResource userById = getUserById(userId);
        userById
            .roles()
            .clientLevel(getClientResource().getId())
            .add(List.of(getClientRole(roleName)));
    }
    
    public void removeClientRole(String userId, String roleName) {
        UserResource userById = getUserById(userId);
        userById
            .roles()
            .clientLevel(getClientResource().getId())
            .remove(List.of(getClientRole(roleName)));
    }

    public UserResource getUserById(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

}
