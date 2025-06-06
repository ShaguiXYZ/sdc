package com.shagui.sdc.service.impl;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.dto.sse.EventDTO;
import com.shagui.sdc.api.dto.sse.EventType;
import com.shagui.sdc.service.SseService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Implementation of the {@link SseService} interface for handling Server-Sent
 * Events (SSE).
 * This service allows the server to push events to the client over an HTTP
 * connection.
 * It uses Reactor's {@link Sinks} and {@link Flux} to manage event streams and
 * keep-alive signals.
 */
@Service
public class SseServiceImpl implements SseService {
    private final Sinks.Many<EventDTO> sink;
    private final Flux<EventDTO> keepAliveFlux;

    /**
     * Initializes the SSE service with a multicast sink for event publishing
     * and a periodic keep-alive flux to maintain the connection.
     */
    public SseServiceImpl() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.keepAliveFlux = Flux.interval(Duration.ofSeconds(30))
                .map(i -> createKeepAliveEvent());
    }

    /**
     * Provides a merged flux of events and keep-alive signals.
     * The keep-alive signals are sent every 30 seconds to ensure the connection
     * remains active.
     *
     * @return a {@link Flux} of {@link EventDTO} containing events and keep-alive
     *         signals
     */
    @Override
    public Flux<EventDTO> asFlux() {
        return Flux.merge(sink.asFlux(), keepAliveFlux);
    }

    /**
     * Emits a new event to the connected clients.
     * If the event is null, it will be ignored.
     *
     * @param event the {@link EventDTO} to emit
     */
    @Override
    public void emit(EventDTO event) {
        if (event == null) {
            // Log a warning if the event is null
            System.out.println("Warning: Attempted to emit a null event.");
            return;
        }
        this.sink.tryEmitNext(event);
    }

    /**
     * Creates a keep-alive event to maintain the SSE connection.
     *
     * @return a {@link EventDTO} of type KEEP_ALIVE
     */
    private EventDTO createKeepAliveEvent() {
        return EventDTO.of("", EventType.KEEP_ALIVE, "");
    }
}
