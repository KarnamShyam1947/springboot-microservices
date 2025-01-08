package com.shyam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.shyam.utils.JwtAuthConverter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthConverter jwtAuthConverter; 
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        security.csrf(csrf -> csrf.disable());

        security.authorizeHttpRequests(
            authorizer -> authorizer
                            .requestMatchers("/", "/api/v1/auth/**").permitAll()
                            .requestMatchers("/api/v1/roles/**").hasRole("client_admin")
                            .anyRequest().authenticated()
        );

        security.oauth2ResourceServer(
            oauth2 -> oauth2.jwt(
                jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)
            )
        );

        security.sessionManagement(
            session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return security.build();
    }
    
}
