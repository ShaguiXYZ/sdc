package com.shagui.sdc.api.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TimeCoverageDTO {
	private Float coverage;
	private Date analysisDate;
}
