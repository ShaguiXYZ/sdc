package com.shagui.sdc.api.domain;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageInfo {
	private int pageIndex;
	private int pageSize;
	private int pages;
	private long elements;
	
	public PageInfo(Page<?> page) {
		this.pageIndex = page.getNumber();
		this.pageSize = page.getNumberOfElements();
		this.pages = page.getTotalPages();
		this.elements = page.getTotalElements();
	}
}
