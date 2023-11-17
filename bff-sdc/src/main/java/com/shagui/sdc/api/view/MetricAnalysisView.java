package com.shagui.sdc.api.view;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MetricAnalysisView {
	private Date analysisDate;
	private MetricView metric;
	private AnalysisValuesView analysisValues;
	private Float coverage;
	private boolean blocker;

	public MetricAnalysisView(MetricAnalysisDTO source) {
		BeanUtils.copyProperties(source, this);

		this.metric = CastFactory.getInstance(MetricView.class).parse(source.getMetric());
		this.analysisValues = CastFactory.getInstance(AnalysisValuesView.class).parse(source.getAnalysisValues());
	}
}
