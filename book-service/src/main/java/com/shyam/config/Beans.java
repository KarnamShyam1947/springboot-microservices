package com.shyam.config;

import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Beans {

    @Value("${application.service-url.author-service}")
    private String authorServiceBaseUrl;
    
    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }
   
    @Bean
    DateTimeFormatter getFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return formatter;
    }

    @Bean(name = "authorServiceRestTemplate")
    RestTemplate authorRestTemplate() {
        return new RestTemplateBuilder()
                    .rootUri(authorServiceBaseUrl)
                    .build();
    }

}

