package com.shagui.analysis.util;

import java.util.Date;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shagui.analysis.api.dto.AnalysisValuesDTO;
import com.shagui.analysis.api.dto.ArchitectureDTO;
import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.ComponentHistoricalCoverageDTO;
import com.shagui.analysis.api.dto.ComponentTypeDTO;
import com.shagui.analysis.api.dto.DepartmentDTO;
import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.api.dto.MetricAnalysisStateDTO;
import com.shagui.analysis.api.dto.MetricDTO;
import com.shagui.analysis.api.dto.SquadDTO;
import com.shagui.analysis.core.exception.ApiError;
import com.shagui.analysis.model.ArchitectureModel;
import com.shagui.analysis.model.ComponentAnalysisModel;
import com.shagui.analysis.model.ComponentHistoricalCoverageModel;
import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.ComponentTypeModel;
import com.shagui.analysis.model.DepartmentModel;
import com.shagui.analysis.model.MetricModel;
import com.shagui.analysis.model.SquadModel;
import com.shagui.analysis.util.validations.MetricValidations;

import feign.FeignException;

public class Mapper {

	private static MapperConfig config;

	private Mapper() {
	}

	public static void setConfig(MapperConfig config) {
		Mapper.config = config;
	}

	public static ApiError parse(FeignException ex) throws JSONException, JsonProcessingException {
		JSONObject json = new JSONObject(ex.contentUTF8());
		return config.getObjectMapper().readValue(json.toString(), ApiError.class);
	}

	public static MetricAnalysisDTO parse(ComponentAnalysisModel source) {
		MetricDTO metric = parse(source.getMetric());
		AnalysisValuesDTO analysisValues = new AnalysisValuesDTO(source.getWeight(), source.getValue(),
				source.getExpectedValue(), source.getGoodValue(), source.getPerfectValue());

		MetricAnalysisDTO target = new MetricAnalysisDTO(source.getId().getComponentAnalysisDate(), metric,
				analysisValues, null);

		target.setCoverage(MetricValidations.validate(target));

		return target;
	}

	public static MetricModel parse(MetricDTO source) {
		return config.getObjectMapper().convertValue(source, MetricModel.class);
	}

	public static MetricDTO parse(MetricModel source) {
		MetricDTO target = new MetricDTO(source.getId(), source.getName(), source.getType(), source.getValueType(),
				source.getValidation());

		return target;
	}

	public static ComponentModel parse(ComponentDTO source) {
		return config.getObjectMapper().convertValue(source, ComponentModel.class);
	}

	public static ComponentDTO parse(ComponentModel source) {
		String coverageStr = ComponentUtils.propertyValue(source, Ctes.COMPONENT_PROPERTIES.COMPONENT_COVERAGE);
		Float coverage = coverageStr == null ? null : Float.valueOf(coverageStr);
		String analysisTimestampStr = ComponentUtils.propertyValue(source,
				Ctes.COMPONENT_PROPERTIES.COMPONENT_ANALYSIS_DATE);
		Date analysisDate = analysisTimestampStr == null ? null : new Date(Long.valueOf(analysisTimestampStr));

		ComponentDTO target = new ComponentDTO(source.getId(), source.getName(), analysisDate, coverage,
				parse(source.getComponentTypeArchitecture().getComponentType()),
				parse(source.getComponentTypeArchitecture().getArchitecture()), parse(source.getSquad()));

		return target;
	}

	public static ComponentTypeModel parse(ComponentTypeDTO source) {
		return config.getObjectMapper().convertValue(source, ComponentTypeModel.class);
	}

	public static ComponentTypeDTO parse(ComponentTypeModel source) {
		ComponentTypeDTO target = new ComponentTypeDTO(source.getId(), source.getName());

		return target;
	}

	public static ArchitectureModel parse(ArchitectureDTO source) {
		return config.getObjectMapper().convertValue(source, ArchitectureModel.class);
	}

	public static ArchitectureDTO parse(ArchitectureModel source) {
		ArchitectureDTO target = new ArchitectureDTO(source.getId(), source.getName());

		return target;
	}

	public static DepartmentDTO parse(DepartmentModel source) {
		DepartmentDTO target = new DepartmentDTO(source.getId(), source.getName());

		return target;
	}

	public static SquadModel parse(SquadDTO source) {
		return config.getObjectMapper().convertValue(source, SquadModel.class);
	}

	public static SquadDTO parse(SquadModel source) {
		MetricAnalysisStateDTO metricState = config.squadService.squadState(source.getId(), new Date());

		SquadDTO target = new SquadDTO(source.getId(), source.getName(), Mapper.parse(source.getDepartment()),
				metricState.getCoverage());

		return target;
	}

	public static ComponentHistoricalCoverageDTO parse(ComponentHistoricalCoverageModel source) {
		ComponentHistoricalCoverageDTO target = new ComponentHistoricalCoverageDTO(source.getId().getAnalysisDate(),
				source.getCoverage());

		return target;
	}

}
