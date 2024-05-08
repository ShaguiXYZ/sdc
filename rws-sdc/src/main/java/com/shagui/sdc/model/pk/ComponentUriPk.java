package com.shagui.sdc.model.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ComponentUriPk implements Serializable {

	@Column(name = "component_id", nullable = false)
	private int componentId;

	@Column(name = "uri_name", nullable = false)
	private String uriName;

}
