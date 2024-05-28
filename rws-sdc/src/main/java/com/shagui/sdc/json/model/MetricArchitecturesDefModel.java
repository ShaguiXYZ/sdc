package com.shagui.sdc.json.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class MetricArchitecturesDefModel {
    private String componentType;
    private String architecture;
    private String network;
    private String deploymentType;
    private String platform;
    private String language;
    private List<MetricDefModel> metrics;
}
