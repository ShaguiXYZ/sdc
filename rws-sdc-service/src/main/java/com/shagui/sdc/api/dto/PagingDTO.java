package com.shagui.sdc.api.dto;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagingDTO {
	private int pageIndex;
	private int pageSize;
	private Integer total;
	
	public PagingDTO(Page<?> page) {
		this.pageIndex = page.getNumber();
		this.pageSize = page.getNumberOfElements();
		this.total = page.getTotalPages();
	}
}
