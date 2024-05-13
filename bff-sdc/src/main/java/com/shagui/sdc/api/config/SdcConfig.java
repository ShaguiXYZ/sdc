package com.shagui.sdc.api.config;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class SdcConfig {
    private static String bffVersion;
    private String copyright;
    private String rwsVersion;
    private AnalysisConfig analysis;
    private ViewConfig view;
    private SecurityConfig security;

    public static void setBffVersion(String bffVersion) {
        SdcConfig.bffVersion = bffVersion;
    }

    public String getBffVersion() {
        return SdcConfig.bffVersion;
    }
}
