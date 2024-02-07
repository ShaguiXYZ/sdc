package com.shagui.sdc.api.domain;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

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

	public PageInfo(@NonNull PageInfo source) {
		BeanUtils.copyProperties(source, this);
	}
}
