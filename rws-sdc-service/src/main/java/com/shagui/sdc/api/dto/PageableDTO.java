package com.shagui.sdc.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageableDTO<T> {	
	private PagingDTO paging;
	private List<T> page;
	
	public PageableDTO(List<T> page) {
		this.page = page;
		this.paging = new PagingDTO(0, this.page.size(), 1);
	}
}
