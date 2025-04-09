package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.sdc.api.dto.sse.EventFactory;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Flux;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
public interface SseEventApi {
    @Operation(summary = "Retrieve SSE events", description = "Streams server-sent events (SSE) to the client.")
    @GetMapping(value = "sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<EventFactory.EventDTO> getEvents();
}
