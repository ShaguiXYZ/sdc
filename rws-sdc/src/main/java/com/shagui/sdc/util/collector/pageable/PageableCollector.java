package com.shagui.sdc.util.collector.pageable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.springframework.data.domain.Page;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.PageInfo;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PageableCollector<T> implements Collector<T, PageableAccumulator<T>, PageData<T>> {
	private PageInfo paging;

	public PageableCollector(Page<?> page) {
		this.paging = new PageInfo(page);
	}

	@Override
	public Supplier<PageableAccumulator<T>> supplier() {
		return () -> new PageableAccumulator<>(paging);
	}

	@Override
	public BiConsumer<PageableAccumulator<T>, T> accumulator() {
		return PageableAccumulator::accumulate;
	}

	@Override
	public BinaryOperator<PageableAccumulator<T>> combiner() {
		return PageableAccumulator::combine;
	}

	@Override
	public Function<PageableAccumulator<T>, PageData<T>> finisher() {
		return PageableAccumulator::finish;
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Set.of(Characteristics.CONCURRENT);
	}
}
