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

import com.shagui.sdc.model.pk.ComponentHistoricalCoveragePk;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "component_historical_coverages")
public class ComponentHistoricalCoverageModel implements ModelInterface<ComponentHistoricalCoveragePk> {
	@EmbeddedId
	private ComponentHistoricalCoveragePk id;

	@Column(name = "component_historical_coverage_vaue")
	private float coverage;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@MapsId("componentId")
	@JoinColumn(name = "component_id")
	private ComponentModel component;

	public ComponentHistoricalCoverageModel(ComponentModel component, Date componentAnalysisDate, float coverage) {
		this.id = new ComponentHistoricalCoveragePk(component.getId(), componentAnalysisDate);
		this.component = component;
		this.coverage = coverage;
	}
}
