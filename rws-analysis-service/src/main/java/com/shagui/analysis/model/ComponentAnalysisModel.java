package com.shagui.analysis.model;

import com.shagui.analysis.model.pk.ComponentAnalysisPk;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "component_analysis")
public class ComponentAnalysisModel implements ModelInterface<ComponentAnalysisPk> {
	@EmbeddedId
	private ComponentAnalysisPk id;

	@Column(name = "component_analysis_value")
	private String value;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "component_type_architecture_id")
	private ComponentTypeArchitectureModel componentTypeArchitecture;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@MapsId("componentId")
	@JoinColumn(name = "component_id")
	private ComponentModel component;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@MapsId("metricId")
	@JoinColumn(name = "metric_id")
	private MetricModel metric;

	@Transient
	private String expectedValue;

	@Transient
	private String goodValue;

	@Transient
	private String perfectValue;

	public ComponentAnalysisModel(ComponentModel component, MetricModel metric, String value) {
		this.id = new ComponentAnalysisPk(component.getId(), metric.getId());

		this.component = component;
		this.componentTypeArchitecture = component.getComponentTypeArchitecture();
		this.metric = metric;
		this.value = value;
	}

}
