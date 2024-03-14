package com.example.stresstestservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder
                .build();
    }
}
