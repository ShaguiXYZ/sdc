package com.shagui.sdc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.SseEventApi;
import com.shagui.sdc.api.dto.sse.EventDTO;
import com.shagui.sdc.api.dto.sse.EventType;
import com.shagui.sdc.service.SseService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
public class SseEventController implements SseEventApi {
    private final SseService sseService;

    @Override
    public Flux<EventDTO> getEvents(String workflowId, List<EventType> types) {
        return this.sseService.asFlux().filter(event -> event.getWorkflowId().equals(workflowId))
                .filter(null == types ? event -> true : event -> types.contains(event.getType()));
    }
}