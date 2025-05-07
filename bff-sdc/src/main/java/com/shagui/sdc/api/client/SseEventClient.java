package com.shagui.sdc.api.client;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.boot.CommandLineRunner;

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
 * @howto server-sent events client
 * @howto start consuming events on application startup (implementing
 *        CommandLineRunner)
 */
@Slf4j
@Service
public class SseEventClient implements CommandLineRunner {
    private final SseProperties properties;
    private final WebClient webClient;
    private final SseService sseService;

    public SseEventClient(SseProperties properties, WebClient webClient, SseService sseService) {
        this.properties = properties;
        this.webClient = webClient;
        this.sseService = sseService;
    }

    /**
     * Starts consuming Server-Sent Events (SSE) from the configured endpoint.
     * This method retrieves the event stream and processes each event.
     */
    private void startConsuming() {
        validateProperties();

        Flux<EventDTO> eventStream = this.webClient.get()
                .uri("/sse")
                .retrieve()
                .bodyToFlux(EventDTO.class)
                .retryWhen(Retry.backoff(properties.getRetry().getMaxAttempts(),
                        Duration.ofMillis(properties.getRetry().getBackoffPeriod())));

        eventStream.subscribe(
                sseService::emit,
                throwable -> log.error("Error receiving SSE: {}", throwable.getMessage(), throwable),
                () -> log.info("SSE stream completed."));
    }

    /**
     * Validates the required properties for SSE consumption.
     * Logs warnings if any property is misconfigured.
     */
    private void validateProperties() {
        if (properties.getRetry().getMaxAttempts() <= 0) {
            log.warn("SSE retry maxAttempts is not properly configured. Defaulting to 3.");
            properties.getRetry().setMaxAttempts(3);
        }
        if (properties.getRetry().getBackoffPeriod() <= 0) {
            log.warn("SSE retry backoffPeriod is not properly configured. Defaulting to 1000ms.");
            properties.getRetry().setBackoffPeriod(1000);
        }
    }

    @Override
    public void run(String... args) {
        log.info("Starting SSE consumption on application startup...");
        startConsuming();
    }
}
