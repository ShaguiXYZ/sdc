package com.shagui.analysis.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagingDTO {
	private int pageIndex;
	private int pageSize;
	private int total;
}
