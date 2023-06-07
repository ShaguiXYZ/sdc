package com.shagui.sdc.api.dto.input;

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
	private String architecture;
	private int squad;
	private List<ComponentPropertyInput> properties;
	private List<String> uriNames;
}
