package com.shagui.sdc.test.utils;

import java.util.Date;

import com.shagui.sdc.api.view.AnalysisValuesView;
import com.shagui.sdc.api.view.ArchitectureView;
import com.shagui.sdc.api.view.ComponentTypeView;
import com.shagui.sdc.api.view.ComponentView;
import com.shagui.sdc.api.view.DepartmentView;
import com.shagui.sdc.api.view.MetricAnalysisView;
import com.shagui.sdc.api.view.MetricView;
import com.shagui.sdc.api.view.SquadView;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;

public class ViewDataUtils {

	private ViewDataUtils() {
	}

	public static DepartmentView createDepartment() {
		DepartmentView data = new DepartmentView();
		data.setId(DataUtils.DEFAULT_DEPARTMENT_ID);
		data.setName("department name");
		data.setCoverage(50f);

		return data;
	}

	public static SquadView createSquad() {
		SquadView data = new SquadView();
		data.setId(DataUtils.DEFAULT_SQUAD_ID);
		data.setDepartment(createDepartment());
		data.setName("squad name");
		data.setCoverage(50f);

		return data;
	}

	public static ArchitectureView createArchitecture() {
		ArchitectureView data = new ArchitectureView();
		data.setId(DataUtils.DEFAULT_ARCHITECTURE_ID);
		data.setName("architecture name");

		return data;
	}

	public static MetricView createMetric(AnalysisType type, MetricValueType valueType, MetricValidation validation) {
		MetricView data = new MetricView();
		data.setId(DataUtils.DEFAULT_METRIC_ID);
		data.setName("architecture name");
		data.setType(type);
		data.setValidation(validation);
		data.setValueType(valueType);

		return data;
	}

	public static ComponentView createComponent() {
		ComponentView data = new ComponentView();
		data.setId(DataUtils.DEFAULT_COMPONENT_ID);
		data.setName("component name");
		data.setSquad(createSquad());
		data.setArchitecture(createArchitecture());

		return data;
	}

	public static AnalysisValuesView createAnalysisValues() {
		AnalysisValuesView data = new AnalysisValuesView();
		data.setMetricValue("value");
		data.setExpectedValue("expected");
		data.setGoodValue("good");
		data.setPerfectValue("perfect");

		return data;
	}

	public static MetricAnalysisView createMetricAnalysis() {
		MetricAnalysisView data = new MetricAnalysisView();
		data.setAnalysisDate(new Date());
		data.setCoverage(50f);

		return data;
	}

	public static ComponentTypeView createComponentType() {
		ComponentTypeView data = new ComponentTypeView();
		data.setId(DataUtils.DEFAULT_COMPONENT_TYPE_ID);
		data.setName("component type name");

		return data;
	}
}
