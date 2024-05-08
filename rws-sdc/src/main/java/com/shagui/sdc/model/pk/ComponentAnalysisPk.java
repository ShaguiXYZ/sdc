package com.shagui.sdc.model.pk;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ComponentAnalysisPk implements Serializable {
	@Column(name = "component_id")
	private int componentId;

	@Column(name = "metric_id")
	private int metricId;

	@CreationTimestamp
	@Column(name = "analysis_date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date analysisDate;
}
