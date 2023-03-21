package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.repository.JpaCommonRepository;
import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.service.MetricService;
import com.shagui.sdc.util.Mapper;

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
