package com.shagui.sdc.api.domain;

import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reference {
    private Integer componentId;
    private String componentName;
    private Integer metricId;
    private String metricName;

    public Reference(ComponentModel component) {
        this.componentId = component.getId();
        this.componentName = component.getName();
    }

    public Reference(ComponentModel component, MetricModel metric) {
        this(component);

        this.metricId = metric.getId();
        this.metricName = metric.getName();
    }
}
