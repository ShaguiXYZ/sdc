package com.shagui.sdc.api.dto.sonar;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasuresSonarDTO {
	private List<MeasureSonarDTO> measures;
}
