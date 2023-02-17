package com.shagui.analysis.api.dto.sonar;

import java.util.List;

import lombok.Data;

@Data
public class MeasuresSonarDTO {
	private List<MeasureSonarDTO> measures;
}
