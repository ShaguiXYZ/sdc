package com.shagui.sdc.service.impl;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.dto.sse.EventFactory;
import com.shagui.sdc.api.dto.sse.EventType;
import com.shagui.sdc.service.SseService;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * (SSE - is a technology where a browser receives automatic updates from a
 * server via HTTP connection)
 * 
 * @howto: server-sent events service
 */
@Slf4j
@Service
public class SseServiceImpl implements SseService {
    private final Sinks.Many<EventFactory.EventDTO> sink;
    private final Flux<Long> keepAliveFlux;

    public SseServiceImpl() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.keepAliveFlux = Flux.interval(Duration.ofSeconds(30)); // Send keep alive every 30 seconds
        // prevent memory leak
        this.sink.asFlux().onBackpressureBuffer().subscribe();
    }

    @Override
    public Flux<EventFactory.EventDTO> asFlux() {
        return Flux.merge(sink.asFlux(), keepAliveFlux.map(i -> EventFactory.event("", EventType.KEEP_ALIVE, "")));
    }

    @Override
    public void emitError(EventFactory.EventDTO event) {
        if (event != null && EventType.ERROR.equals(event.getType())) {
            if (StringUtils.isNotBlank(event.getWorkflowId())) {
                this.sink.tryEmitNext(event);
            }

            log.error("SSE: {}", event.getMessage());
        }
    }
}
