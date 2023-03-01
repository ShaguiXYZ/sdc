package com.shagui.analysis.util.collector.pageable;

import java.util.ArrayList;
import java.util.List;

import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.api.dto.PagingDTO;

public class PageableAccumulator<T> {
	private PagingDTO paging;
	private List<T> paginated = new ArrayList<>();
	
	public PageableAccumulator(PagingDTO paging) {
		this.paging = paging;
	}

	public void accumulate(T data) {
		paginated.add(data);
	}
	
	public PageableAccumulator<T> combine(PageableAccumulator<T> other) {
		PageableDTO<T> dto = other.finish();
		this.paginated.addAll(dto.getPage());
		return this;
	}
	
	public PageableDTO<T> finish() {
		if (paging == null) {
			paging = new PagingDTO(0, paginated.size(), 1);
		}
		
		return new PageableDTO<>(paging, paginated);
	}
}
