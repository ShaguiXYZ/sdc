package com.shagui.sdc.api.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeCoverageDTO {
	private Float coverage;
	private Date analysisDate;
}
