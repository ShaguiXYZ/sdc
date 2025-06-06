package com.shagui.sdc.test.utils;

import java.util.Date;

import com.shagui.sdc.api.dto.AnalysisValuesDTO;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.SquadDTO;

public class DtoDataUtils {

	private DtoDataUtils() {
	}

	public static DepartmentDTO createDepartment() {
		DepartmentDTO data = new DepartmentDTO();
		data.setId(DataUtils.DEFAULT_DEPARTMENT_ID);
		data.setName("department name");
		data.setCoverage(50f);

		return data;
	}

	public static SquadDTO createSquad() {
		SquadDTO data = new SquadDTO();
		data.setId(DataUtils.DEFAULT_SQUAD_ID);
		data.setDepartment(createDepartment());
		data.setName("squad name");
		data.setCoverage(50f);

		return data;
	}

	public static MetricDTO createMetric(String type, String valueType, String validation) {
		MetricDTO data = new MetricDTO();
		data.setId(DataUtils.DEFAULT_METRIC_ID);
		data.setName("architecture name");
		data.setType(type);
		data.setValidation(validation);
		data.setValueType(valueType);

		return data;
	}

	public static ComponentDTO createComponent() {
		ComponentDTO data = new ComponentDTO();
		data.setId(DataUtils.DEFAULT_COMPONENT_ID);
		data.setName("component name");
		data.setSquad(createSquad());

		return data;
	}

	public static AnalysisValuesDTO createAnalysisValues() {
		AnalysisValuesDTO data = new AnalysisValuesDTO();
		data.setMetricValue("value");
		data.setExpectedValue("expected");
		data.setGoodValue("good");
		data.setPerfectValue("perfect");

		return data;
	}

	public static MetricAnalysisDTO createMetricAnalysis() {
		MetricAnalysisDTO data = new MetricAnalysisDTO();
		data.setAnalysisDate(new Date());
		data.setCoverage(50f);

		return data;
	}
}
