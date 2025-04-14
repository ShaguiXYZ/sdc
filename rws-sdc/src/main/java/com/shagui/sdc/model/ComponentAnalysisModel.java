package com.shagui.sdc.model;

import java.util.Date;

import com.shagui.sdc.model.pk.ComponentAnalysisPk;
import com.shagui.sdc.util.jpa.ModelInterface;

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

/**
 * Represents the analysis of a component with respect to a specific metric.
 * This entity is mapped to the "component_analysis" table in the database.
 * It contains information about the component, the metric being analyzed,
 * the analysis value, and other related metadata.
 * 
 * <p>
 * This class uses JPA annotations for ORM mapping and Lombok annotations
 * for generating boilerplate code such as getters, setters, and constructors.
 * </p>
 * 
 * <p>
 * The primary key for this entity is a composite key represented by
 * {@link ComponentAnalysisPk}, which includes the component ID, metric ID,
 * and analysis date.
 * </p>
 * 
 * <p>
 * Transient fields are used for additional computed or temporary values
 * that are not persisted in the database.
 * </p>
 * 
 * <p>
 * Relationships:
 * <ul>
 * <li>{@link ComponentTypeArchitectureModel}: Represents the architecture type
 * of the component.</li>
 * <li>{@link ComponentModel}: Represents the component being analyzed.</li>
 * <li>{@link MetricModel}: Represents the metric being analyzed.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Constructors:
 * <ul>
 * <li>A no-argument constructor is provided by Lombok's
 * {@code @NoArgsConstructor} annotation.</li>
 * <li>A parameterized constructor is available for initializing the model with
 * a component, metric, and value.</li>
 * <li>An extended constructor allows initialization with additional details
 * such as analysis date and blocker status.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Fields:
 * <ul>
 * <li>{@code id}: Composite primary key for the entity.</li>
 * <li>{@code metricValue}: The value of the metric for the component.</li>
 * <li>{@code blocker}: Indicates whether the analysis result is a blocker.</li>
 * <li>{@code componentTypeArchitecture}: The architecture type of the
 * component.</li>
 * <li>{@code component}: The component being analyzed.</li>
 * <li>{@code metric}: The metric being analyzed.</li>
 * <li>{@code expectedValue}, {@code goodValue}, {@code perfectValue}: Transient
 * fields for additional analysis values.</li>
 * <li>{@code weight}: Transient field representing the weight of the
 * metric.</li>
 * <li>{@code coverage}: Transient field representing the coverage value.</li>
 * </ul>
 * </p>
 * 
 * @author Shagui
 * @version 1.0
 * @see ComponentAnalysisPk
 * @see ComponentTypeArchitectureModel
 * @see ComponentModel
 * @see MetricModel
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "component_analysis")
public class ComponentAnalysisModel implements ModelInterface<ComponentAnalysisPk> {
	@EmbeddedId
	private ComponentAnalysisPk id;

	@Column(name = "value", nullable = true, length = 255)
	private String metricValue;

	@Column(nullable = false)
	private boolean blocker;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "component_type_architecture_id", nullable = false)
	private ComponentTypeArchitectureModel componentTypeArchitecture;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("componentId")
	@JoinColumn(name = "component_id", nullable = false)
	private ComponentModel component;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("metricId")
	@JoinColumn(name = "metric_id", nullable = false)
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
