package com.shagui.analysis.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.analysis.api.dto.MetricDTO;
import com.shagui.analysis.model.MetricModel;
import com.shagui.analysis.repository.JpaCommonRepository;
import com.shagui.analysis.repository.MetricRepository;
import com.shagui.analysis.service.MetricService;
import com.shagui.analysis.util.Mapper;

@Service
public class MetricServiceImpl implements MetricService {
	private JpaCommonRepository<MetricRepository, MetricModel, Integer> metricRepository;
	
	public MetricServiceImpl(MetricRepository metricRepository) {
		this.metricRepository = () -> metricRepository;
	}
	
	@Override
	public MetricDTO create(MetricDTO metric) {
		MetricModel model = metricRepository.create(Mapper.parse(metric));
		
		return Mapper.parse(model);
	}
	
	@Override
	public MetricDTO update(Integer id, MetricDTO metric) {
		MetricModel model = metricRepository.update(id, Mapper.parse(metric));
		
		return Mapper.parse(model);
	}
}
