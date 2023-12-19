package com.shagui.sdc.core.configuration.dto;

import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.Ctes.AnalysisConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisConfig {
    private int precision;
    private int trendDeep;
    private float trendHeat;

    public AnalysisConfig() {
        this.precision = Ctes.AnalysisConstants.PRECISION;
        this.trendDeep = Ctes.AnalysisConstants.TREND_DEEP;
        this.trendHeat = Ctes.AnalysisConstants.TREND_HEAT;
    }

    public float trendValue(Float trend) {
        return trend == null || AnalysisConstants.TREND_HEAT > Math.abs(trend) ? 0.0f : trend;
    }
}
