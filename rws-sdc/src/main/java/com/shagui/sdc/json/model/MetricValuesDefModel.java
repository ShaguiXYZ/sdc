package com.shagui.sdc.json.model;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class MetricValuesDefModel {
    private int weight;
    private String value;
    private String goodValue;
    private String perfectValue;
}
