package com.shagui.analysis.model.pk;

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
public class ComponentHistoricalCoveragePk  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4115998161045778024L;

	@Column(name = "component_id")
	private int componentId;
	
	@CreationTimestamp
	@Column(name = "component_historical_coverage_date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date analysisDate;
}
