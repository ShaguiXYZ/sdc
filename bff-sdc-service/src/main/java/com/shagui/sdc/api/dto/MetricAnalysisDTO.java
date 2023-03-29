package com.shagui.sdc.api.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.view.MetricAnalysisView;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricAnalysisDTO {
	private Date analysisDate;
	private MetricDTO metric;
	private AnalysisValuesDTO analysisValues;
	private Float coverage;

	public MetricAnalysisDTO(MetricAnalysisView source) {
		BeanUtils.copyProperties(source, this);

		this.metric = CastFactory.getInstance(MetricDTO.class).parse(source.getMetric());
		this.analysisValues = CastFactory.getInstance(AnalysisValuesDTO.class).parse(source.getAnalysisValues());
	}

}
