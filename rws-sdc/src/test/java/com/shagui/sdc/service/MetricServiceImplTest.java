package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.service.impl.MetricServiceImpl;

class MetricServiceImplTest {

	@InjectMocks
	private MetricServiceImpl service;

	@Mock
	private MetricRepository metricRepositoryMock;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void constructorTest() {

		MetricServiceImpl service = new MetricServiceImpl(metricRepositoryMock);
		assertNotNull(service);

	}
	
}