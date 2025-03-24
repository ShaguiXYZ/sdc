package com.shagui.sdc.util.collector.pageable;

import java.util.ArrayList;
import java.util.List;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.PageInfo;

public class PageableAccumulator<T> {
	private PageInfo paging;
	private final List<T> paginated = new ArrayList<>();

	public PageableAccumulator(PageInfo paging) {
		this.paging = paging;
	}

	public void accumulate(T data) {
		paginated.add(data);
	}

	public PageableAccumulator<T> combine(PageableAccumulator<T> other) {
		this.paginated.addAll(other.paginated);
		return this;
	}

	public PageData<T> finish() {
		if (paging == null) {
			paging = new PageInfo(0, paginated.size(), 1, paginated.size());
		}
		return new PageData<>(paging, paginated);
	}
}
