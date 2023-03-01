package com.shagui.analysis.util.collector;

import java.util.stream.Collector;

import org.springframework.data.domain.Page;

import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.util.collector.pageable.PageableCollector;

public final class SdcCollectors {

	private SdcCollectors() {}
	
	public static <T> Collector<T, ?, PageableDTO<T>> toPageable() {
		return new PageableCollector<>();
	}

	
	public static <T> Collector<T, ?, PageableDTO<T>> toPageable(Page<?> page) {
		return new PageableCollector<>(page);
	}
}
