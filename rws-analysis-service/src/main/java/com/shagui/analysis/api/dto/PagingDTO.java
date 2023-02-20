package com.shagui.analysis.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 
 * @author Shagui
 *
 */
@Data
@AllArgsConstructor
public class PagingDTO {
	private int pageIndex;
	private int pageSize;
	private int total;
}
