package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.SseEventApi;
import com.shagui.sdc.api.dto.sse.EventFactory;
import com.shagui.sdc.service.SseService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
public class SseEventController implements SseEventApi {
    private final SseService sseService;

    @Override
    public Flux<EventFactory.EventDTO> getEvents() {
        return this.sseService.asFlux();
    }
}