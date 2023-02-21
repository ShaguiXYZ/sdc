package com.shagui.analysis.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 * @author Shagui
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingDTO {
	private int pageIndex;
	private int pageSize;
	private int total;
}
