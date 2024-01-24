package com.shagui.sdc.util;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shagui.sdc.api.dto.AnalysisValuesDTO;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.ComponentTagDTO;
import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.api.dto.SummaryViewDTO;
import com.shagui.sdc.api.dto.TagDTO;
import com.shagui.sdc.api.dto.TimeCoverageDTO;
import com.shagui.sdc.core.configuration.AppConfig;
import com.shagui.sdc.core.exception.ApiError;
import com.shagui.sdc.core.exception.SdcMapperException;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTagModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.model.SummaryViewModel;
import com.shagui.sdc.model.TagModel;

import feign.FeignException;

public class Mapper {

	private static MapperConfig config;

	private Mapper() {
	}

	protected static void setConfig(MapperConfig config) {
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

		return new MetricAnalysisDTO(source.getId().getAnalysisDate(), metric, analysisValues, source.getCoverage(),
				source.isBlocker());
	}

	public static MetricModel parse(MetricDTO source) {
		return config.getObjectMapper().convertValue(source, MetricModel.class);
	}

	public static MetricDTO parse(MetricModel source) {
		return new MetricDTO(source.getId(), source.getName(), source.getValue(), source.getDescription(),
				source.getType(), source.getValueType(), source.getValidation(), source.isBlocker());
	}

	public static ComponentTypeArchitectureDTO parse(ComponentTypeArchitectureModel source) {
		return new ComponentTypeArchitectureDTO(source.getId(), source.getComponentType(), source.getArchitecture(),
				source.getNetwork(), source.getDeploymentType(), source.getPlatform(), source.getLanguage());
	}

	public static ComponentTypeArchitectureModel parse(ComponentTypeArchitectureDTO source) {
		return config.getObjectMapper().convertValue(source, ComponentTypeArchitectureModel.class);
	}

	public static ComponentModel parse(ComponentDTO source) {
		return config.getObjectMapper().convertValue(source, ComponentModel.class);
	}

	public static ComponentDTO parse(ComponentModel source) {
		List<TagDTO> tags = source.getTags().stream().map(Mapper::parse).toList();

		return new ComponentDTO(source.getId(), source.getName(), parse(source.getComponentTypeArchitecture()),
				source.getAnalysisDate(), source.getCoverage(),
				AppConfig.getConfig().getAnalysis().trendValue(source.getTrend()),
				source.isBlocked(), parse(source.getSquad()), tags);
	}

	public static DepartmentDTO parse(DepartmentModel source) {
		return new DepartmentDTO(source.getId(), source.getName(), source.getCoverage(),
				AppConfig.getConfig().getAnalysis().trendValue(source.getTrend()));
	}

	public static SquadDTO parse(SquadModel source) {
		return new SquadDTO(source.getId(), source.getName(), Mapper.parse(source.getDepartment()),
				source.getCoverage(), AppConfig.getConfig().getAnalysis().trendValue(source.getTrend()));
	}

	public static TagDTO parse(TagModel source) {
		return new TagDTO(source.getId(), source.getName(), source.isAnalysisTag());
	}

	public static TagModel parse(TagDTO source) {
		if (!source.getName().matches("^[a-zA-Z]\\w*$")) {
			throw new SdcMapperException("Tag name must begin with a letter and contain only alphanumeric");
		}

		return config.getObjectMapper().convertValue(source, TagModel.class);
	}

	public static ComponentTagDTO parse(ComponentTagModel source) {
		return new ComponentTagDTO(parse(source.getComponent()), parse(source.getTag()), source.isAnalysisTag());
	}

	public static TimeCoverageDTO parse(ComponentHistoricalCoverageModel source) {
		return new TimeCoverageDTO(source.getCoverage(), source.getId().getAnalysisDate());
	}

	public static SummaryViewDTO parse(SummaryViewModel source) {
		return new SummaryViewDTO(source.getId().getId(), source.getName(), source.getId().getType(),
				source.getCoverage());
	}

}
