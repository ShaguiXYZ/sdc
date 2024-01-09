package com.shagui.sdc.api.dto.sse;

import java.util.Date;

import lombok.Getter;

@Getter
public class EventDTO {
    private EventType type;
    private String workflowId;
    private String message;
    private long date;
    private ReferenceDTO reference;

    public static EventDTO of(String workflowId, EventType type, String message) {
        EventDTO event = new EventDTO();

        event.type = type;
        event.workflowId = workflowId;
        event.message = message;
        event.date = (new Date()).getTime();

        return event;
    }
}
