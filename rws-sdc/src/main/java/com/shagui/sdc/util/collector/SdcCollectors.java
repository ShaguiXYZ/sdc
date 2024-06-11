package com.shagui.sdc.util.collector;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.util.collector.pageable.PageableCollector;

/**
 * @howto {@link SdcCollectors#toPageable()}<br>
 * @howto {@link SdcCollectors#toReversedList()}<br>
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

	public static <T> Collector<T, ?, List<T>> toReversedList() {
		return Collectors.collectingAndThen(Collectors.toList(), list -> {
			Collections.reverse(list);

			return list;
		});
	}
}
