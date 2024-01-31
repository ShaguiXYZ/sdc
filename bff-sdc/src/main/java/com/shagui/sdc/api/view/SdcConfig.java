package com.shagui.sdc.api.view;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class SdcConfig {
    private static String bffVersion;
    private String rwsVersion;
    private AnalysisConfig analysis;
    private JpaConfig jpa;

    public static void setBffVersion(String bffVersion) {
        SdcConfig.bffVersion = bffVersion;
    }

    public String getBffVersion() {
        return SdcConfig.bffVersion;
    }
}
