package com.shagui.sdc.service;

import java.util.List;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.util.ComponentUtils;

public interface DependabotService extends AnalysisInterface {
	@Override
	default List<MetricModel> metrics(ComponentModel component) {
		return ComponentUtils.metricsByType(component, AnalysisType.DEPENDABOT);
	}
}
