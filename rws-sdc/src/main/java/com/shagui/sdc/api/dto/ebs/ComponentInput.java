package com.shagui.sdc.api.dto.ebs;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComponentInput {
	private String name;
	private String componentType;
	private String network;
	private String deploymentType;
	private String platform;
	private String architecture;
	private String language;
	private int squad;
	private List<ComponentPropertyInput> properties;
	private List<String> uriNames;
}
