package com.shagui.analysis.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.analysis.api.dto.ArchitectureDTO;
import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.ComponentTypeDTO;
import com.shagui.analysis.repository.ComponentAnalysisRepository;
import com.shagui.analysis.repository.ComponentRepository;
import com.shagui.analysis.repository.ComponentTypeArchitectureRepository;
import com.shagui.analysis.service.impl.AnalysisServiceImpl;
import com.shagui.analysis.service.impl.ComponentServiceImpl;

class ComponentServiceTest {
	
	@InjectMocks
	ComponentServiceImpl service;
	
	@Mock
	ComponentRepository componentsRepository;
	
	@Mock
	ComponentTypeArchitectureRepository componentTypeArchitectureRepository;
	
	@BeforeEach
	void init(){
	    MockitoAnnotations.openMocks(this);
	}

	@Test
	void componentServiceConstructorTest() {
		
		ComponentServiceImpl service = new ComponentServiceImpl(componentsRepository,
				componentTypeArchitectureRepository);
		
		assertNotNull(service);
	}

}
