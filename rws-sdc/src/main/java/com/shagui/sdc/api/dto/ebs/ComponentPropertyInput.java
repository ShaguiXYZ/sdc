package com.shagui.sdc.api.dto.ebs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComponentPropertyInput {
	private String name;
	private String value;
}