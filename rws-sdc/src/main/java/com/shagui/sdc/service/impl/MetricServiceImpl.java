package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.core.exception.ExceptionCodes;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.service.MetricService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class MetricServiceImpl implements MetricService {
	private JpaCommonRepository<MetricRepository, MetricModel, Integer> metricRepository;

	public MetricServiceImpl(MetricRepository metricRepository) {
		this.metricRepository = () -> metricRepository;
	}

	@Override
	public MetricDTO metric(int metricId) {
		return Mapper.parse(metricRepository.findExistingId(metricId));
	}

	@Override
	public MetricDTO metric(String metricName, AnalysisType metricType) {
		return Mapper
				.parse(metricRepository.repository().findByNameIgnoreCaseAndType(metricName, metricType)
						.orElseThrow(() -> new JpaNotFoundException(ExceptionCodes.NOT_FOUND_METRIC,
								String.format("no result found for metric '%s' and type '%s'", metricName,
										metricType.name()))));
	}

	@Override
	public PageData<MetricDTO> metrics() {
		return metricRepository.findAll().stream().map(Mapper::parse).collect(SdcCollectors.toPageable());
	}

	@Override
	public MetricDTO create(MetricDTO metric) {
		return Mapper.parse(metricRepository.create(Mapper.parse(metric)));
	}

	@Override
	public MetricDTO update(Integer id, MetricDTO metric) {
		return Mapper.parse(metricRepository.update(id, Mapper.parse(metric)));
	}
}
