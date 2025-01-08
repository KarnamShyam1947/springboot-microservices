package com.shyam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.shyam.utils.JwtAuthConverter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    
    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    private String[] GET_WHITE_LIST_URLs = {
        "/eureka/**",
        "/api/v1/books/**",
        "/api/v1/author/**",
        "/api/v1/auth/**"
    };
    
    private String[] POST_WHITE_LIST_URLs = {
        "/api/v1/auth/**"
    };

    private String[] OPTIONS_WHITE_LIST_URLs = {
        "/v3/api-docs/**", 
        "/configuration/**", 
        "/swagger-ui/**",
        "/swagger-resources/**", 
        "/swagger-ui.html", 
        "/webjars/**", 
        "/api-docs/**",
        "/actuator/health"
    };

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity security) {

        security.csrf(csrf -> csrf.disable());

        security.oauth2ResourceServer(
            oauth -> oauth.jwt(
                jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)
            )
        );

        security.authorizeExchange(
            exchange -> exchange
                        .pathMatchers(HttpMethod.GET, GET_WHITE_LIST_URLs).permitAll()
                        .pathMatchers(HttpMethod.OPTIONS, OPTIONS_WHITE_LIST_URLs).permitAll()
                        .pathMatchers(HttpMethod.POST, POST_WHITE_LIST_URLs).permitAll()
                        .anyExchange().authenticated()
        );

        return security.build();
    }

    // @Bean
    // WebSecurityCustomizer webSecurityCustomizer() {

    //     return (web) -> {

    //         // web.ignoring().requestMatchers(
    //         //     HttpMethod.POST,
    //         //     "/public/**"
    //         // );

    //         // web.ignoring().requestMatchers(
    //         //     HttpMethod.DELETE,
    //         //     "/public/**"
    //         // );

    //         // web.ignoring().requestMatchers(
    //         //     HttpMethod.PUT,
    //         //     "/public/**"
    //         // );
            
    //         web.ignoring().requestMatchers(
    //             HttpMethod.GET,
    //             "/api/v1/books/**",
    //             "/api/v1/author/**",
    //             "/eureka/**"
    //         );

    //         web.ignoring().requestMatchers(
    //             HttpMethod.OPTIONS,
    //             "/**"
    //         )
    //         .requestMatchers(
    //         "/v3/api-docs/**", "/configuration/**", "/swagger-ui/**",
    //             "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/api-docs/**",
    //             "/actuator/health"
    //         );

    //     };
    // }


    
}
