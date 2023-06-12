package com.shagui.sdc.api.domain;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageInfo {
	private int pageIndex;
	private int pageSize;
	private int pages;
	private long elements;

	public PageInfo(PageInfo source) {
		BeanUtils.copyProperties(source, this);
	}
}
