package com.shagui.sdc.core.configuration.sse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SseConfig {
    private final SseProperties properties;

    public SseConfig(SseProperties properties) {
        this.properties = properties;
    }

    @Bean
    WebClient webClient() {
        return WebClient.create(properties.getUrl());
    }
}
