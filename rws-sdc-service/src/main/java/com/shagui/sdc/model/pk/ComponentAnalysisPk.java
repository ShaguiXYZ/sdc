package com.shagui.sdc.model.pk;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ComponentAnalysisPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4115998161045778024L;

	@Column(name = "component_id")
	private int componentId;

	@Column(name = "metric_id")
	private int metricId;

	@CreationTimestamp
	@Column(name = "component_analysis_date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date componentAnalysisDate;
}
