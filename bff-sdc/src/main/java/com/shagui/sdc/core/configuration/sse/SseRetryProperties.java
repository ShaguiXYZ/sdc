package com.shagui.sdc.core.configuration.sse;

import com.shagui.sdc.util.Ctes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SseRetryProperties {
    private long maxAttempts = Ctes.SSE_RETRY_MAX_ATTEMPTS;
    private long backoffPeriod = Ctes.SSE_RETRY_BACKOFF_PERIOD;
}
