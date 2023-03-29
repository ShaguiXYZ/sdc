package com.shagui.sdc.api.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageData<T> {
	private PageInfo paging;
	private List<T> page;

	public PageData(List<T> page) {
		this.page = page;
		this.paging = new PageInfo(0, this.page.size(), 1, this.page.size());
	}
}
