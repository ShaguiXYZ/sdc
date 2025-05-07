package com.shagui.sdc.service.impl;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shagui.sdc.api.dto.sse.EventFactory;
import com.shagui.sdc.api.dto.sse.EventType;
import com.shagui.sdc.service.SseService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * (SSE - is a technology where a browser receives automatic updates from a
 * server via HTTP connection)
 * 
 * @howto server-sent events service
 */
@Slf4j
@Service
public class SseServiceImpl implements SseService {
    private final Sinks.Many<EventFactory.EventDTO> sink;
    private final Flux<EventFactory.EventDTO> keepAliveFlux;

    public SseServiceImpl() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.keepAliveFlux = Flux.interval(Duration.ofSeconds(30))
                .map(i -> createKeepAliveEvent());
    }

    @Override
    public Flux<EventFactory.EventDTO> asFlux() {
        // Combina eventos emitidos con eventos de "mantener vivo"
        return Flux.merge(sink.asFlux(), keepAliveFlux);
    }

    @Override
    public void emitError(EventFactory.EventDTO event) {
        if (event == null) {
            log.warn("SSE: Intento de emitir un evento nulo.");
            return;
        }

        if (EventType.ERROR.equals(event.getType()) && StringUtils.hasText(event.getWorkflowId())) {
            this.sink.tryEmitNext(event);
            log.error("SSE Error emitido: WorkflowId={}, Mensaje={}", event.getWorkflowId(), event.getMessage());
        } else {
            log.warn("SSE: Evento no válido para emitir como error. Tipo={}, WorkflowId={}",
                    event.getType(), event.getWorkflowId());
        }
    }

    private EventFactory.EventDTO createKeepAliveEvent() {
        // Crea un evento de tipo KEEP_ALIVE
        return EventFactory.event("", EventType.KEEP_ALIVE, "");
    }
}
