package com.shyam.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Beans {

    @Value("${application.service-url.book-service}")
    private String bookServiceUrl;

    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                    .rootUri(bookServiceUrl)
                    .build();
    }
    
}
