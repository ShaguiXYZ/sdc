package com.shagui.sdc.util;

import java.util.Date;
import java.util.Objects;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shagui.sdc.api.dto.AnalysisValuesDTO;
import com.shagui.sdc.api.dto.ArchitectureDTO;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.ComponentTypeDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.api.dto.TimeCoverageDTO;
import com.shagui.sdc.core.exception.ApiError;
import com.shagui.sdc.model.ArchitectureModel;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTypeModel;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.util.validations.MetricValidations;

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
		String analysisTimestampStr = ComponentUtils.propertyValue(source,
				Ctes.COMPONENT_PROPERTIES.COMPONENT_ANALYSIS_DATE);
		Date analysisDate = analysisTimestampStr == null ? null : new Date(Long.valueOf(analysisTimestampStr));

		ComponentDTO target = new ComponentDTO(source.getId(), source.getName(), analysisDate, source.getCoverage(),
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
		int numSquads = source.getSquads().size();
		Float coverage = source.getSquads().stream().map(SquadModel::getCoverage).filter(Objects::nonNull).reduce(0f,
				(a, b) -> a + b);

		DepartmentDTO target = new DepartmentDTO(source.getId(), source.getName(),
				numSquads > 0 ? coverage / numSquads : 0);

		return target;
	}

	public static SquadDTO parse(SquadModel source) {
		SquadDTO target = new SquadDTO(source.getId(), source.getName(), Mapper.parse(source.getDepartment()),
				source.getCoverage());

		return target;
	}

	public static TimeCoverageDTO parse(ComponentHistoricalCoverageModel source) {
		TimeCoverageDTO target = new TimeCoverageDTO(source.getCoverage(), source.getId().getAnalysisDate());

		return target;
	}
}
