package com.shagui.sdc.api.dto.sse;

/**
 * Enum representing the types of events that can occur.
 */
public enum EventType {
    /**
     * Represents an error event.
     */
    ERROR,

    /**
     * Represents an informational event.
     */
    INFO,

    /**
     * Represents a keep-alive event.
     */
    KEEP_ALIVE
}
