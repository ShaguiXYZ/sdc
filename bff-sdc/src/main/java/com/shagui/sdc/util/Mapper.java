package com.shagui.sdc.util;

import java.util.stream.Collectors;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.PageInfo;
import com.shagui.sdc.api.domain.TimeCoverage;
import com.shagui.sdc.core.exception.ApiError;

import feign.FeignException;

public class Mapper {
	public static ApiError parse(FeignException ex) throws JSONException, JsonProcessingException {
		ApiError error = new ApiError();
		error.setStatus(HttpStatus.valueOf(ex.status()));
		error.setMessage(ex.getMessage());

		return error;
	}

	public static <V, D> PageData<V> parse(PageData<D> source, Class<V> clazz) {
		PageData<V> target = new PageData<>();

		target.setPaging(CastFactory.getInstance(PageInfo.class).parse(source.getPaging()));
		target.setPage(
				source.getPage().stream().map(CastFactory.getInstance(clazz)::parse).collect(Collectors.toList()));

		return target;
	}

	public static <V> HistoricalCoverage<V> parse(HistoricalCoverage<?> source, Class<V> clazz) {
		HistoricalCoverage<V> target = new HistoricalCoverage<>();

		target.setData(CastFactory.getInstance(clazz).parse(source.getData()));
		target.setHistorical(Mapper.parse(source.getHistorical(), TimeCoverage.class));

		return target;
	}
}
