package com.shagui.analysis.api.dto.sonar;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @author Shagui
 *
 */
@Getter
@Setter
public class PagingSonarDTO {
	private int pageIndex;
	private int pageSize;
	private int total;
}
