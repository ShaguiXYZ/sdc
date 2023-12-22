package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shagui.sdc.api.dto.sse.EventDTO;
import com.shagui.sdc.api.dto.sse.EventType;
import com.shagui.sdc.service.SseService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import java.time.Duration;

@Service
public class SseServiceImpl implements SseService {
    private final Sinks.Many<EventDTO> sink;
    private final Flux<Long> keepAliveFlux;

    public SseServiceImpl() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.keepAliveFlux = Flux.interval(Duration.ofSeconds(30)); // Send keep alive every 30 seconds
        // prevent memory leak
        this.sink.asFlux().onBackpressureBuffer().subscribe();
    }

    @Override
    public Flux<EventDTO> asFlux() {
        return Flux.merge(sink.asFlux(), keepAliveFlux.map(i -> new EventDTO("", EventType.KEEP_ALIVE, "")));
    }

    @Override
    public void emit(String workflowId, String message) {
        if (StringUtils.hasText(message)) {
            this.sink.tryEmitNext(new EventDTO(workflowId, EventType.INFO, message));
        }
    }

    @Override
    public void emit(String workflowId, RuntimeException exception) {
        if (exception != null) {
            this.sink.tryEmitNext(new EventDTO(workflowId, exception));
        }
    }

    @Override
    public void emit(EventDTO event) {
        if (event != null) {
            this.sink.tryEmitNext(event);
        }
    }
}
