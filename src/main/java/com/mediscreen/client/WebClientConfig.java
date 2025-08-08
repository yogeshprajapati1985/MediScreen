package com.mediscreen.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient myServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080") // Replace with your desired hostname and port
                .build();
    }
}


