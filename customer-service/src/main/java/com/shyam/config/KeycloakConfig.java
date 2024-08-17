package com.shyam.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin.client-id}")
    private String clientId;
    
    @Value("${keycloak.serviceUrl}")
    private String serviceUrl;

    @Value("${keycloak.client-secret}")
    private String clientSecret;
    
    @Bean
    Keycloak getKeycloak() {
        return KeycloakBuilder
                .builder()
                .realm(realm)
                .clientId(clientId)
                .serverUrl(serviceUrl)
                .clientSecret(clientSecret)
                .grantType("client_credentials")
                .build();
    }

}
