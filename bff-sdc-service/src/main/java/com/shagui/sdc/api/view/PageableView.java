package com.shagui.sdc.api.view;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageableView<T> {	
	private PagingView paging;
	private List<T> page;
}
