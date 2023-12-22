package com.shagui.sdc.util;

public class Ctes {
    public static final String HEADER_WORKFLOW_ID = "WorkflowId";
    public static final long SSE_RETRY_MAX_ATTEMPTS = Long.MAX_VALUE;
    public static final long SSE_TIMEOUT = 30l * 1000; // 30 seconds
    public static final long SSE_RETRY_BACKOFF_PERIOD = 10l * 1000; // 10 seconds

    private Ctes() {
    }
}
