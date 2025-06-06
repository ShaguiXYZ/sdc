package com.shagui.sdc.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.dto.ServiceDataDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.service.AnalysisInterface;
import com.shagui.sdc.util.ComponentUtils;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.DictioraryReplacement;
import com.shagui.sdc.util.analysis.CustomAnalysisFunctions;

@Service(Ctes.AnalysisServicesTypes.REMOTE)
public class CustomAnalysisServiceImpl implements AnalysisInterface {
    @Override
    public List<MetricModel> metrics(ComponentModel component) {
        return ComponentUtils.metricsByType(component, AnalysisType.REMOTE);
    }

    @Override
    public List<ComponentAnalysisModel> analyze(String workflowId, ComponentModel component) {
        List<ComponentAnalysisModel> result = List.of();
        List<MetricModel> metrics = metrics(component);

        metrics.parallelStream().map(analyzeMetric(workflowId, component)).forEach(result::add);

        return result;
    }

    private Function<MetricModel, ComponentAnalysisModel> analyzeMetric(String workflowId, ComponentModel component) {
        return metric -> {
            String fn = DictioraryReplacement.fn(metric.getValue()).orElseGet(metric::getValue);
            String value = MetricLibrary.Library.valueOf(fn.toUpperCase())
                    .apply(new ServiceDataDTO(workflowId, component, metric))
                    .orElseGet(() -> CustomAnalysisFunctions.notDataFound
                            .apply(new ServiceDataDTO(workflowId, component, metric)).get());

            ComponentAnalysisModel analysis = new ComponentAnalysisModel();
            analysis.setComponent(component);
            analysis.setMetric(metric);
            analysis.setMetricValue(value);

            return analysis;
        };
    }

    private static class MetricLibrary {
        enum Library {
            CALL_DEFAULT_ANALYSIS(CustomAnalysisFunctions.callAnalysis);

            private Function<ServiceDataDTO, Optional<String>> fn;

            private Library(Function<ServiceDataDTO, Optional<String>> fn) {
                this.fn = fn;
            }

            public Optional<String> apply(ServiceDataDTO data) {
                return this.fn.apply(data);
            }
        }
    }
}
