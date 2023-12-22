package com.shagui.sdc.core.configuration.sse;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.shagui.sdc.util.Ctes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Configuration
@ConfigurationProperties(prefix = "services.sse")
public class SseProperties {
    private String url;
    private long timeout = Ctes.SSE_TIMEOUT;
    private SseRetryProperties retry = new SseRetryProperties();
}
