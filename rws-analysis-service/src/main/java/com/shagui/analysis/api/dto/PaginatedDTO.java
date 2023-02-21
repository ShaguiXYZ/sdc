package com.shagui.analysis.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaginatedDTO<T> {	
	private PagingDTO paging;
	private List<T> data;
}
