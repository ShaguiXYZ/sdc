package com.shagui.sdc.service;

import com.shagui.sdc.api.dto.sse.EventFactory;

import reactor.core.publisher.Flux;

public interface SseService {
    void emit(EventFactory.EventDTO event);

    Flux<EventFactory.EventDTO> asFlux();
}
