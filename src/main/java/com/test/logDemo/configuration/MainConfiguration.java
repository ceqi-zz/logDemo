package com.test.logDemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class MainConfiguration  {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
