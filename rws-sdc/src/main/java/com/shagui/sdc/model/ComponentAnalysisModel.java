package com.shagui.sdc.model;

import java.util.Date;

import com.shagui.sdc.model.pk.ComponentAnalysisPk;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "component_analysis")
public class ComponentAnalysisModel implements ModelInterface<ComponentAnalysisPk> {
	@EmbeddedId
	private ComponentAnalysisPk id;

	@Column(name = "value")
	private String metricValue;

	private boolean blocker;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_type_architecture_id")
	private ComponentTypeArchitectureModel componentTypeArchitecture;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("componentId")
	@JoinColumn(name = "component_id")
	private ComponentModel component;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("metricId")
	@JoinColumn(name = "metric_id")
	private MetricModel metric;

	@Transient
	private String expectedValue;

	@Transient
	private String goodValue;

	@Transient
	private String perfectValue;

	@Transient
	private int weight;

	@Transient
	private Float coverage;

	public ComponentAnalysisModel(ComponentModel component, MetricModel metric, String value) {
		this(component, metric, value, new Date(), false);
	}

	public ComponentAnalysisModel(ComponentModel component, MetricModel metric, String value, Date analysisDate,
			boolean blocker) {
		this.id = new ComponentAnalysisPk(component.getId(), metric.getId(), analysisDate);
		this.component = component;
		this.componentTypeArchitecture = component.getComponentTypeArchitecture();
		this.metric = metric;
		this.metricValue = value;
		this.blocker = blocker;
	}
}
