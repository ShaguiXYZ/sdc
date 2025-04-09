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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the historical coverage data for a component at a specific
 * analysis date.
 * 
 * Relationships:
 * - Many-to-One with ComponentModel: Each historical coverage entry is linked
 * to a specific component.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "component_historical_coverages")
public class ComponentHistoricalCoverageModel implements ModelInterface<ComponentHistoricalCoveragePk> {
	@EmbeddedId
	private ComponentHistoricalCoveragePk id;

	@Column(name = "historical_coverage_vaue")
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
