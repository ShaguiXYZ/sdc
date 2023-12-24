package com.shagui.sdc.api.dto.sse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferenceDTO {
    private Integer componentId;
    private String componentName;
    private Integer metricId;
    private String metricName;
}
