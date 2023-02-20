package com.shagui.analysis.model.pk;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class ComponentAnalysisPk  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4115998161045778024L;

	@Column(name = "component_id")
	private int componentId;
	
	@CreationTimestamp
	@Column(name = "component_analysis_date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date componentAnalysisDate;
	
	@Column(name = "metric_id")
	private int metricId;

	public ComponentAnalysisPk(int componentId, int metricId) {
		this.componentId = componentId;
		this.metricId = metricId;
		this.componentAnalysisDate = new Date();
	}
}
