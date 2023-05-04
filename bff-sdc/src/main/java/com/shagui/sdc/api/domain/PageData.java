package com.shagui.sdc.api.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageData<T> {	
	private PageInfo paging;
	private List<T> page;
}
