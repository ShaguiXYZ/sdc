package com.shagui.sdc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentTypeArchitectureDTO {
	private Integer id;
	private String componentType;
	private String architecture;
	private String network;
	private String deploymentType;
	private String platform;
	private String language;
}
