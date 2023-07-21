package com.shagui.sdc.api.dto.ebs;

import java.util.List;

import org.springframework.util.StringUtils;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.ComponentParamsModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.util.ComponentUtils;

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
		model.setName(this.getName());

		this.getProperties().stream().map(ComponentPropertyInput::asModel).forEach(prop -> {
			prop.setComponent(model);
			model.getProperties().add(prop);
		});

		ComponentParamsModel staticComponentParams = StaticRepository.componentParams().stream()
				.filter(param -> param.getType().equals(this.getComponentType())).findFirst()
				.orElseGet(this::defaultComponentParams);

		staticComponentParams
				// filters out parameters that do not exist in the model
				.getParams().stream()
				.filter(param -> model.getProperties().stream().map(ComponentPropertyModel::getName)
						.noneMatch(propertyName -> propertyName.equals(param.getName())))
				.map(param -> {
					String value = ComponentUtils.componentType(model)
							.map(data -> model.getName().replace(data, param.getPre())).orElse(model.getName());

					if (StringUtils.hasText(param.getPost())) {
						value = String.format("%s-%s", value, param.getPost());
					}

					return new ComponentPropertyModel(model, param.getName(), value);
				}).forEach(model.getProperties()::add);

		return model;
	}

	private ComponentParamsModel defaultComponentParams() {
		return StaticRepository.componentParams().stream().filter(param -> param.getType().equals("_")).findFirst()
				.orElseThrow(() -> new SdcCustomException("Not component params config present"));
	}

}
