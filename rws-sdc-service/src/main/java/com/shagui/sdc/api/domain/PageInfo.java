package com.shagui.sdc.api.domain;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageInfo {
	private int pageIndex;
	private int pageSize;
	private Integer total;
	
	public PageInfo(Page<?> page) {
		this.pageIndex = page.getNumber();
		this.pageSize = page.getNumberOfElements();
		this.total = page.getTotalPages();
	}
}
