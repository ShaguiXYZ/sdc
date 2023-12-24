package com.shagui.sdc.service.impl;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.dto.sse.EventFactory;
import com.shagui.sdc.api.dto.sse.EventType;
import com.shagui.sdc.service.SseService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

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
    public void emit(EventFactory.EventDTO event) {
        if (event != null) {
            this.sink.tryEmitNext(event);
        }
    }
}
