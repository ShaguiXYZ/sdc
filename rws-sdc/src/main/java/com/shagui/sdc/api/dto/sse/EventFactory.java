package com.shagui.sdc.api.dto.sse;

import java.util.Date;

import com.shagui.sdc.api.domain.Reference;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class EventFactory {

    private EventFactory() {
    }

    public static EventDTO event(String workflowId, EventType type, String message) {
        return new EventDTO(workflowId, type, message);
    }

    public static EventDTO event(String workflowId, SdcCustomException exception) {
        return new EventDTO(workflowId, exception);
    }

    @Getter
    public static class EventDTO {
        private EventType type;
        private String workflowId;
        private String message;
        private long date;
        private Reference reference;

        private EventDTO(@NotNull String workflowId, EventType type, String mesage) {
            this.type = type;
            this.message = mesage;
            this.workflowId = workflowId;
            this.date = (new Date()).getTime();
        }

        private EventDTO(@NotNull String workflowId, @NotNull SdcCustomException exception) {
            this(workflowId, EventType.ERROR, exception.getMessage());
            this.reference = exception.getReference();
        }

        public EventDTO referencedBy(ComponentModel component) {
            return referencedBy(new Reference(component));
        }

        public EventDTO referencedBy(ComponentModel component, MetricModel metric) {
            return referencedBy(new Reference(component, metric));
        }

        public EventDTO referencedBy(Reference reference) {
            this.reference = reference;

            return this;
        }
    }
}
