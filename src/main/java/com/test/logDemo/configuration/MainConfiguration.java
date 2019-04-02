package com.test.logDemo.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class MainConfiguration  {
    private static final int NTHREADS = 4;
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public ExecutorService executorService(){return Executors.newFixedThreadPool(NTHREADS);}
}
