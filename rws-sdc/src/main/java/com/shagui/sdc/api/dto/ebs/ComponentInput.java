package com.shagui.sdc.api.dto.ebs;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
		String componentName = this.name;
		model.setName(componentName);

		List<ComponentPropertyModel> propertiesToAdd = this.getProperties().stream()
				.filter(property -> !property.isToDelete())
				.map(ComponentPropertyInput::asModel)
				.peek(prop -> prop.setComponent(model))
				.collect(Collectors.toList());

		Set<String> propertyNames = propertiesToAdd.stream()
				.map(ComponentPropertyModel::getName)
				.collect(Collectors.toSet());

		ComponentParamsModel staticComponentParams = StaticRepository.componentParams().stream()
				.filter(param -> param.getType().equals(this.componentType))
				.findFirst()
				.orElseGet(this::defaultComponentParams);

		List<ComponentPropertyModel> defaultProperties = staticComponentParams.getParams().stream()
				.filter(param -> !propertyNames.contains(param.getName()))
				.map(param -> {
					String value = Arrays
							.asList(componentName.replaceFirst("^[^\\_\\-]*", param.getPre()), param.getPost())
							.stream()
							.filter(StringUtils::hasText)
							.collect(Collectors.joining("-"));

					return new ComponentPropertyModel(model, param.getName(), value);
				})
				.collect(Collectors.toList());

		model.getProperties().addAll(propertiesToAdd);
		model.getProperties().addAll(defaultProperties);

		return model;
	}

	private ComponentParamsModel defaultComponentParams() {
		return StaticRepository.componentParams().stream().filter(param -> param.getType().equals("_")).findFirst()
				.orElseThrow(() -> new SdcCustomException("Not component params config present"));
	}

}
