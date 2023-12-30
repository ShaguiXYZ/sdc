package com.shagui.sdc.api.client;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.shagui.sdc.api.dto.sse.EventDTO;
import com.shagui.sdc.core.configuration.sse.SseProperties;
import com.shagui.sdc.service.SseService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

/**
 * (SSE - is a technology where a browser receives automatic updates from a
 * server via HTTP connection)
 * 
 * @howto: server-sent events client
 */
@Slf4j
@Service
public class SseEventClient {
    private final SseProperties properties;
    private final WebClient webClient;
    private final SseService sseService;

    public SseEventClient(SseProperties properties, WebClient webClient, SseService sseService) {
        this.properties = properties;
        this.webClient = webClient;
        this.sseService = sseService;

        consume();
    }

    private void consume() {
        Flux<EventDTO> eventStream = this.webClient.get()
                .uri("/events")
                .retrieve()
                .bodyToFlux(EventDTO.class)
                .retryWhen(Retry.backoff(properties.getRetry().getMaxAttempts(),
                        Duration.ofMillis(properties.getRetry().getBackoffPeriod())));

        eventStream.subscribe(
                sseService::emit,
                throwable -> log.error("Error receiving SSE: {}", throwable.getMessage()),
                () -> log.info("SSE Completed!!!"));
    }
}
