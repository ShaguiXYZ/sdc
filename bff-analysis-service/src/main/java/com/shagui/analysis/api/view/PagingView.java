package com.shagui.analysis.api.view;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagingView {
	private int pageIndex;
	private int total;
}
