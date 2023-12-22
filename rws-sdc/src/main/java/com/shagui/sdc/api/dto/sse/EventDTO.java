package com.shagui.sdc.api.dto.sse;

import lombok.Getter;

@Getter
public class EventDTO {
    private EventType type;
    private String workflowId;
    private String message;
    private String date;

    public EventDTO(String workflowId, EventType type, String mesage) {
        this.type = type;
        this.message = mesage;
        this.workflowId = workflowId;
        this.date = String.valueOf(System.currentTimeMillis());
    }

    public EventDTO(String workflowId, RuntimeException exception) {
        this(workflowId, EventType.ERROR, exception.getMessage());
    }
}
