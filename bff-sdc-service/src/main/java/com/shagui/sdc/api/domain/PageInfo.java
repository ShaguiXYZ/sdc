package com.shagui.sdc.api.domain;

import org.springframework.beans.BeanUtils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageInfo {
	private int pageIndex;
	private int pageSize;
	private int total;
	
	public PageInfo(PageInfo source) {
		BeanUtils.copyProperties(source, this);
	}
}
