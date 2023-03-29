package com.shagui.sdc.util;

import java.util.stream.Collectors;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.PageInfo;
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

	public static <V, D> PageData<V> parse(PageData<D> source, Class<V> clazz) {
		PageData<V> target = new PageData<V>();

		target.setPaging(CastFactory.getInstance(PageInfo.class).parse(source.getPaging()));
		target.setPage(source.getPage().stream().map(CastFactory.getInstance(clazz)::parse).collect(Collectors.toList()));

		return target;
	}
}
