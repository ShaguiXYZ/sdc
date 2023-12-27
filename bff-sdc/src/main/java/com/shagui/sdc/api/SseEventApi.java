package com.shagui.sdc.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.dto.sse.EventDTO;
import com.shagui.sdc.api.dto.sse.EventType;

import feign.Headers;
import reactor.core.publisher.Flux;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
public interface SseEventApi {
    @GetMapping(value = "/events/{workflowId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<EventDTO> getEvents(@PathVariable String workflowId, @RequestParam(required = false) List<EventType> types);
}
