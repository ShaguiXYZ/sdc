package com.shagui.sdc.api.dto.ebs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.ComponentParamsModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;

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

	public ComponentModel asModel() {
		ComponentModel model = new ComponentModel();
		model.setName(this.name);

		this.getProperties().stream().map(ComponentPropertyInput::asModel).forEach(prop -> {
			prop.setComponent(model);
			model.getProperties().add(prop);
		});

		ComponentParamsModel staticComponentParams = StaticRepository.componentParams().stream()
				.filter(param -> param.getType().equals(this.componentType)).findFirst()
				.orElseGet(this::defaultComponentParams);

		staticComponentParams
				.getParams().stream()
				// filters out parameters that do not exist in the model
				.filter(param -> model.getProperties().stream().map(ComponentPropertyModel::getName)
						.noneMatch(propertyName -> propertyName.equals(param.getName())))
				.map(param -> {
					String value = Arrays
							.asList(model.getName().replaceFirst("^[^\\_\\-]*", param.getPre()), param.getPost())
							.stream().filter(StringUtils::hasText).collect(Collectors.joining("-"));

					return new ComponentPropertyModel(model, param.getName(), value);
				}).forEach(model.getProperties()::add);

		return model;
	}

	private ComponentParamsModel defaultComponentParams() {
		return StaticRepository.componentParams().stream().filter(param -> param.getType().equals("_")).findFirst()
				.orElseThrow(() -> new SdcCustomException("Not component params config present"));
	}

}
