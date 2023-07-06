package com.shagui.sdc.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ComponentUriPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8380578041243171512L;

	@Column(name = "component_id", nullable = false)
	private int componentId;

	@Column(name = "uri_name", nullable = false)
	private String uriName;

}
