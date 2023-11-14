package com.shagui.sdc.util;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shagui.sdc.api.dto.AnalysisValuesDTO;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.api.dto.TimeCoverageDTO;
import com.shagui.sdc.core.exception.ApiError;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.SquadModel;

import feign.FeignException;

public class Mapper {

	private static MapperConfig config;

	private Mapper() {
	}

	public static void setConfig(MapperConfig config) {
		Mapper.config = config;
	}

	public static String parse(Object source) throws JsonProcessingException {
		if (source == null) {
			return null;
		}

		return config.getObjectMapper().writeValueAsString(source);
	}

	public static ApiError parse(FeignException ex) {
		ApiError error = new ApiError();
		error.setStatus(HttpStatus.valueOf(ex.status()));
		error.setMessage(ex.getMessage());

		return error;
	}

	public static MetricAnalysisDTO parse(ComponentAnalysisModel source) {
		MetricDTO metric = parse(source.getMetric());
		AnalysisValuesDTO analysisValues = new AnalysisValuesDTO(source.getWeight(), source.getMetricValue(),
				source.getExpectedValue(), source.getGoodValue(), source.getPerfectValue());

		return new MetricAnalysisDTO(source.getId().getAnalysisDate(), metric, analysisValues, source.getCoverage());
	}

	public static MetricModel parse(MetricDTO source) {
		return config.getObjectMapper().convertValue(source, MetricModel.class);
	}

	public static MetricDTO parse(MetricModel source) {
		return new MetricDTO(source.getId(), source.getName(), source.getValue(), source.getDescription(),
				source.getType(), source.getValueType(), source.getValidation());
	}

	public static ComponentTypeArchitectureDTO parse(ComponentTypeArchitectureModel source) {
		return new ComponentTypeArchitectureDTO(source.getComponentType(), source.getArchitecture(),
				source.getNetwork(), source.getDeploymentType(), source.getPlatform(), source.getLanguage());
	}

	public static ComponentTypeArchitectureModel parse(ComponentTypeArchitectureDTO source) {
		return config.getObjectMapper().convertValue(source, ComponentTypeArchitectureModel.class);
	}

	public static ComponentModel parse(ComponentDTO source) {
		return config.getObjectMapper().convertValue(source, ComponentModel.class);
	}

	public static ComponentDTO parse(ComponentModel source) {
		return new ComponentDTO(source.getId(), source.getName(), parse(source.getComponentTypeArchitecture()),
				source.getAnalysisDate(), source.getCoverage(), parse(source.getSquad()));
	}

	public static DepartmentDTO parse(DepartmentModel source) {
		List<Float> coverages = source.getSquads().stream().map(SquadModel::getCoverage).filter(Objects::nonNull)
				.toList();

		int numSquads = coverages.size();

		Float coverage = coverages.stream().reduce(0f, (a, b) -> a + b);
		return new DepartmentDTO(source.getId(), source.getName(), numSquads > 0 ? coverage / numSquads : null);
	}

	public static SquadDTO parse(SquadModel source) {
		return new SquadDTO(source.getId(), source.getName(), Mapper.parse(source.getDepartment()),
				source.getCoverage());
	}

	public static TimeCoverageDTO parse(ComponentHistoricalCoverageModel source) {
		return new TimeCoverageDTO(source.getCoverage(), source.getId().getAnalysisDate());
	}
}
