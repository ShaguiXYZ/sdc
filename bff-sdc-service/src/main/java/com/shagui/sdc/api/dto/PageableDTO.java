package com.shagui.sdc.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableDTO<T> {	
	private PagingDTO paging;
	private List<T> page;
}
