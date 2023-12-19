package com.shagui.sdc.api.view;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class SdcConfig {
    private AnalysisConfig analysis;
    private JpaConfig jpa;
}
