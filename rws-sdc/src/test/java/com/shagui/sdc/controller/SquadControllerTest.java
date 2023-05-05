package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.service.SquadService;

class SquadControllerTest {
	
	@InjectMocks
	private SquadController controller;
	
	@Mock
	private SquadService squadService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void findByIdTest() {
		
		when(squadService.findById(anyInt())).thenReturn(new SquadDTO());
		SquadDTO result = controller.squad(1);
		assertNotNull(result);
	}
	
	@Test
	void findAllPageNullTest() {
		
		PageData<SquadDTO> squadDto = new PageData<SquadDTO>(new ArrayList<SquadDTO>());
		
		when(squadService.findAll()).thenReturn(squadDto);
		PageData<SquadDTO> result = controller.squads(null, 1);
		assertNotNull(result);
	}
	
	@Test
	void findAllPageNotNullTest() {
		
		PageData<SquadDTO> squadDto = new PageData<SquadDTO>(new ArrayList<SquadDTO>());
		
		when(squadService.findAll(any(RequestPageInfo.class))).thenReturn(squadDto);
		PageData<SquadDTO> result = controller.squads(1, 1);
		assertNotNull(result);
	}
	
	@Test
	void squadsByDepartmentPageNullTest() {
		
PageData<SquadDTO> squadDto = new PageData<SquadDTO>(new ArrayList<SquadDTO>());
		
		when(squadService.findByDepartment(anyInt())).thenReturn(squadDto);
		PageData<SquadDTO> result = controller.squadsByDepartment(1, null, 1);
		assertNotNull(result);
	}
	
	@Test
	void squadsByDepartmentPageNotNullTest() {
		
		PageData<SquadDTO> squadDto = new PageData<SquadDTO>(new ArrayList<SquadDTO>());
		
		when(squadService.findByDepartment(anyInt(), any(RequestPageInfo.class))).thenReturn(squadDto);
		PageData<SquadDTO> result = controller.squadsByDepartment(1, 1, 1);
		assertNotNull(result);
	}
	
	

}
