package com.shagui.sdc.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.shagui.sdc.model.pk.ComponentAnalysisPk;

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
	
	@Transient
	private int weight;
	
	public ComponentAnalysisModel(ComponentModel component, MetricModel metric, String value) {
		this(component, metric, value, new Date());
	}
	
	public ComponentAnalysisModel(ComponentModel component, MetricModel metric, String value, Date componentAnalysisDate) {
		this.id = new ComponentAnalysisPk(component.getId(), metric.getId(), componentAnalysisDate);

		this.component = component;
		this.componentTypeArchitecture = component.getComponentTypeArchitecture();
		this.metric = metric;
		this.value = value;
	}

}
