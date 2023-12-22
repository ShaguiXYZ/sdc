package com.shagui.sdc.api.dto.sse;

import lombok.Getter;

@Getter
public class EventDTO {
    private EventType type;
    private String workflowId;
    private String message;
    private String date;

    public static EventDTO of(String workflowId, EventType type, String message) {
        EventDTO event = new EventDTO();

        event.type = type;
        event.workflowId = workflowId;
        event.message = message;
        event.date = String.valueOf(System.currentTimeMillis());

        return event;
    }
}
