package com.shagui.sdc.api.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisConfig {
    private int precision;
    private int trendDeep;
    private float trendHeat;
}
