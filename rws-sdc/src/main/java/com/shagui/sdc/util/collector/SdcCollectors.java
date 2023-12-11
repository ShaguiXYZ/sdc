package com.shagui.sdc.util.collector;

import java.util.stream.Collector;

import org.springframework.data.domain.Page;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.util.collector.pageable.PageableCollector;

/**
 * @howto {@link SdcCollectors#toPageable()}<br>
 */
public final class SdcCollectors {

	private SdcCollectors() {
	}

	public static <T> Collector<T, ?, PageData<T>> toPageable() {
		return new PageableCollector<>();
	}

	public static <T> Collector<T, ?, PageData<T>> toPageable(Page<?> page) {
		return new PageableCollector<>(page);
	}
}
