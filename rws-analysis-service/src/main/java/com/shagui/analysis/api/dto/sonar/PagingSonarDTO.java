package com.shagui.analysis.api.dto.sonar;

import lombok.Data;


/**
 * 
 * @author Shagui
 *
 */
@Data
public class PagingSonarDTO {
	private int pageIndex;
	private int pageSize;
	private int total;
}
