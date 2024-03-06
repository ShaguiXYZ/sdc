package com.shagui.sdc.api.dto.git;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DependabotAlertDTO {
	@JsonProperty("number")
	private Integer id;
	private String state;
	@JsonProperty("security_vulnerability")
	private DependabotVulnerabilityDTO vulnerability;
	@JsonProperty("security_advisory")
	private DependabotAdvisoryDTO advisory;
}
