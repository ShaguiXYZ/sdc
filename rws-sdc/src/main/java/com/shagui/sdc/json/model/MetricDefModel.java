package com.shagui.sdc.json.model;

import java.util.HashMap;
import java.util.Map;

import com.shagui.sdc.enums.AnalysisType;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class MetricDefModel {
    private String metricName;
    private AnalysisType type;
    private Map<String, String> params = new HashMap<>();
    private MetricValuesDefModel values;

}
