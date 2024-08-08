package com.shyam.config;

import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
    
    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }
   
    @Bean
    DateTimeFormatter getFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return formatter;
    }

}

