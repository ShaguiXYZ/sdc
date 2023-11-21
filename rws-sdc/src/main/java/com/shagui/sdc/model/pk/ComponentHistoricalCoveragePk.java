package com.shagui.sdc.model.pk;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ComponentHistoricalCoveragePk implements Serializable {
	@Column(name = "component_id")
	private int componentId;

	@CreationTimestamp
	@Column(name = "historical_coverage_date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date analysisDate;
}
