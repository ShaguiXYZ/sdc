package com.shagui.sdc.service;

import java.util.List;

import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;

public interface AnalysisInterface {
	List<ComponentAnalysisModel> analyze(String workfowId, ComponentModel component);

	List<MetricModel> metrics(ComponentModel component);
}
