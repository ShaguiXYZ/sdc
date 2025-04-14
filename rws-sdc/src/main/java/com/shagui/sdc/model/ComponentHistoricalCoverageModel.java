package com.shagui.sdc.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import com.shagui.sdc.model.pk.ComponentHistoricalCoveragePk;
import com.shagui.sdc.util.jpa.ModelInterface;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the historical coverage data for a specific component.
 * This entity is mapped to the "component_historical_coverages" table in the
 * database.
 * It uses a composite primary key defined by
 * {@link ComponentHistoricalCoveragePk}.
 * 
 * <p>
 * The class includes the following fields:
 * </p>
 * <ul>
 * <li><b>id</b>: The composite primary key, which includes the component ID and
 * the analysis date.</li>
 * <li><b>coverage</b>: The historical coverage value for the component.</li>
 * <li><b>component</b>: The associated {@link ComponentModel} entity,
 * representing the component to which this historical coverage belongs.</li>
 * </ul>
 * 
 * <p>
 * The class provides a no-argument constructor and a parameterized constructor
 * for creating instances with specific values.
 * </p>
 * 
 * <p>
 * Annotations used:
 * </p>
 * <ul>
 * <li><b>@Getter</b> and <b>@Setter</b>: Lombok annotations to generate getter
 * and setter methods for all fields.</li>
 * <li><b>@NoArgsConstructor</b>: Lombok annotation to generate a no-argument
 * constructor.</li>
 * <li><b>@Entity</b>: Specifies that this class is a JPA entity.</li>
 * <li><b>@Table</b>: Specifies the database table name for this entity.</li>
 * <li><b>@EmbeddedId</b>: Indicates that the primary key is an embedded
 * composite key.</li>
 * <li><b>@Column</b>: Maps the "historical_coverage_value" column to the
 * "coverage" field.</li>
 * <li><b>@ManyToOne</b>: Defines a many-to-one relationship with the
 * {@link ComponentModel} entity.</li>
 * <li><b>@MapsId</b>: Maps the "componentId" field in the composite key to the
 * "component_id" column.</li>
 * <li><b>@JoinColumn</b>: Specifies the foreign key column for the relationship
 * with {@link ComponentModel}.</li>
 * </ul>
 * 
 * @author Shagui
 * @see ComponentHistoricalCoveragePk
 * @see ComponentModel
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "component_historical_coverages")
public class ComponentHistoricalCoverageModel implements ModelInterface<ComponentHistoricalCoveragePk> {
	@EmbeddedId
	private ComponentHistoricalCoveragePk id;

	@Column(name = "historical_coverage_value")
	private float coverage;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("componentId")
	@JoinColumn(name = "component_id")
	private ComponentModel component;

	public ComponentHistoricalCoverageModel(ComponentModel component, Date componentAnalysisDate, float coverage) {
		this.id = new ComponentHistoricalCoveragePk(component.getId(), componentAnalysisDate);
		this.component = component;
		this.coverage = coverage;
	}
}
