package com.shagui.sdc.util;

import java.util.stream.Collectors;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shagui.sdc.api.dto.AnalysisValuesDTO;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.ComponentHistoricalCoverageDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricAnalysisStateDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.PageableDTO;
import com.shagui.sdc.api.dto.PagingDTO;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.api.view.AnalysisValuesView;
import com.shagui.sdc.api.view.ComponentHistoricalCoverageView;
import com.shagui.sdc.api.view.ComponentView;
import com.shagui.sdc.api.view.MetricAnalysisStateView;
import com.shagui.sdc.api.view.MetricAnalysisView;
import com.shagui.sdc.api.view.MetricView;
import com.shagui.sdc.api.view.PageableView;
import com.shagui.sdc.api.view.PagingView;
import com.shagui.sdc.api.view.ParseableTo;
import com.shagui.sdc.api.view.SquadView;
import com.shagui.sdc.core.exception.ApiError;

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

	public static PagingDTO parse(PagingView source) {
		return config.getObjectMapper().convertValue(source, PagingDTO.class);
	}

	public static PagingView parse(PagingDTO source) {
		return config.getObjectMapper().convertValue(source, PagingView.class);
	}

	public static ComponentDTO parse(ComponentView source) {
		return config.getObjectMapper().convertValue(source, ComponentDTO.class);
	}

	public static ComponentView parse(ComponentDTO source) {
		return config.getObjectMapper().convertValue(source, ComponentView.class);
	}

	public static MetricDTO parse(MetricView source) {
		return config.getObjectMapper().convertValue(source, MetricDTO.class);
	}

	public static MetricView parse(MetricDTO source) {
		return config.getObjectMapper().convertValue(source, MetricView.class);
	}

	public static AnalysisValuesDTO parse(AnalysisValuesView source) {
		return config.getObjectMapper().convertValue(source, AnalysisValuesDTO.class);
	}

	public static AnalysisValuesView parse(AnalysisValuesDTO source) {
		return config.getObjectMapper().convertValue(source, AnalysisValuesView.class);
	}

	public static SquadDTO parse(SquadView source) {
		return config.getObjectMapper().convertValue(source, SquadDTO.class);
	}

	public static SquadView parse(SquadDTO source) {
		return config.getObjectMapper().convertValue(source, SquadView.class);
	}

	public static MetricAnalysisView parse(MetricAnalysisDTO source) {
		return config.getObjectMapper().convertValue(source, MetricAnalysisView.class);
	}

	public static MetricAnalysisStateView parse(MetricAnalysisStateDTO source) {
		return config.getObjectMapper().convertValue(source, MetricAnalysisStateView.class);
	}

	public static ComponentHistoricalCoverageView parse(ComponentHistoricalCoverageDTO source) {
		return config.getObjectMapper().convertValue(source, ComponentHistoricalCoverageView.class);
	}

	public static <V, D extends ParseableTo<V>> PageableView<V> parse(PageableDTO<D> source) {
		PageableView<V> target = new PageableView<V>();
		target.setPaging(Mapper.parse(source.getPaging()));
		target.setPage(source.getPage().stream().map(ParseableTo::parse).collect(Collectors.toList()));

		return target;
	}
}
