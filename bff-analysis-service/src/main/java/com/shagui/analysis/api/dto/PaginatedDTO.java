package com.shagui.analysis.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginatedDTO<T> {	
	private PagingDTO paging;
	protected List<T> data;
}
