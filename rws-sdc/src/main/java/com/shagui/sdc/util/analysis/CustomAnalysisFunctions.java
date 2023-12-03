package com.shagui.sdc.util.analysis;

import java.util.Optional;
import java.util.function.Function;

import com.shagui.sdc.api.dto.ServiceDataDTO;

public class CustomAnalysisFunctions {
    private CustomAnalysisFunctions() {
    }

    // Metric library fn's
    public static final Function<ServiceDataDTO, Optional<String>> callAnalysis = serviceData -> {
        // Default custom analysis function
        return Optional.of("");
    };

    public static final Function<ServiceDataDTO, Optional<String>> notDataFound = serviceData -> Optional
            .of("Not data found");
}
