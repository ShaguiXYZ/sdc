package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.dto.sse.EventDTO;
import com.shagui.sdc.service.SseService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class SseServiceImpl implements SseService {
    private final Sinks.Many<EventDTO> sink;

    public SseServiceImpl() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @Override
    public Flux<EventDTO> asFlux() {
        return sink.asFlux();
    }

    @Override
    public void emit(EventDTO event) {
        if (event != null) {
            this.sink.tryEmitNext(event);
        }
    }
}
