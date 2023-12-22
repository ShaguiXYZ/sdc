package com.shagui.sdc.service;

import com.shagui.sdc.api.dto.sse.EventDTO;

import reactor.core.publisher.Flux;

public interface SseService {
    void emit(EventDTO event);

    Flux<EventDTO> asFlux();
}
