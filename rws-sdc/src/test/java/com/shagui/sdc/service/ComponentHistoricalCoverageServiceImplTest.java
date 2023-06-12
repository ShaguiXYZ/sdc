package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.nullable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.HistoricalCoverageDTO;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.pk.ComponentHistoricalCoveragePk;
import com.shagui.sdc.repository.ComponentHistoricalCoverageRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.service.impl.ComponentHistoricalCoverageServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;

class ComponentHistoricalCoverageServiceImplTest {
	
	@InjectMocks
	private ComponentHistoricalCoverageServiceImpl service;
	
	@Mock
	private ComponentHistoricalCoverageRepository componentHistoricalCoverageRepository;
	
	@Mock
	private ComponentRepository componentRepository;
	
	@BeforeEach
	void init() {
		
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void constructorTest() {
		
		ComponentHistoricalCoverageServiceImpl constructor = new ComponentHistoricalCoverageServiceImpl(componentRepository, componentHistoricalCoverageRepository);
		assertNotNull(constructor);
	}
	
	@Test
	void historicalCoverageTest() {
		
		Optional<ComponentModel> value = Optional.of(RwsTestUtils.componentModelMock());
		when(componentRepository.findById(anyInt())).thenReturn(value);
		
		List<ComponentHistoricalCoverageModel> list = new ArrayList<>();
		ComponentHistoricalCoveragePk id = new ComponentHistoricalCoveragePk();
		id.setAnalysisDate(new Date(0));
		
		ComponentHistoricalCoverageModel source = new ComponentHistoricalCoverageModel();
		source.setCoverage((float) 90.1);
		source.setId(id);
		list.add(source);
		
		when(componentHistoricalCoverageRepository.findById_ComponentId(anyInt())).thenReturn(list );
		HistoricalCoverageDTO<ComponentDTO> result = service.historicalCoverage(11);
		assertNotNull(result);
	}
	
	@Test
	void historicalCoverage2Test() {
		
		Optional<ComponentModel> value = Optional.of(RwsTestUtils.componentModelMock());
		when(componentRepository.findById(anyInt())).thenReturn(value);
		
		List<ComponentHistoricalCoverageModel> list = new ArrayList<>();
		Page<ComponentHistoricalCoverageModel> page = new PageImpl<ComponentHistoricalCoverageModel>(list);
		ComponentHistoricalCoveragePk id = new ComponentHistoricalCoveragePk();
		id.setAnalysisDate(new Date(0));
		
		ComponentHistoricalCoverageModel source = new ComponentHistoricalCoverageModel();
		source.setCoverage((float) 90.1);
		source.setId(id);
		list.add(source);
		
		when(componentHistoricalCoverageRepository.findById_ComponentId(anyInt(), nullable(Pageable.class))).thenReturn(page );
		HistoricalCoverageDTO<ComponentDTO> result = service.historicalCoverage(11, new RequestPageInfo(1));
		assertNotNull(result);
	}

}
