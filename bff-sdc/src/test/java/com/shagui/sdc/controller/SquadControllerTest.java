package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.api.view.SquadView;
import com.shagui.sdc.service.SquadService;
import com.shagui.sdc.test.utils.DataUtils;
import com.shagui.sdc.test.utils.DtoDataUtils;

class SquadControllerTest {
	@InjectMocks
	SquadController squadController;

	@Mock
	SquadService squadService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void squadTest() {
		SquadDTO dto = DtoDataUtils.createSquad();
		when(squadService.squad(anyInt())).thenReturn(dto);
		SquadView result = squadController.squad(DataUtils.DEFAULT_SQUAD_ID);
		assertNotNull(result);
		assertEquals(result.getId(), dto.getId());
		assertEquals(result.getName(), dto.getName());
	}

	@Test
	void squadsTest() {
		when(squadService.squads(any())).thenReturn(DataUtils.createEmptyPageData());
		PageData<SquadView> result = squadController.squads(null);
		assertNotNull(result);
	}

	@Test
	void departmentSquads() {
		when(squadService.departmentSquads(anyInt(),any())).thenReturn(DataUtils.createEmptyPageData());
		PageData<SquadView> result = squadController.departmentSquads(DataUtils.DEFAULT_SQUAD_ID, null);
		assertNotNull(result);		
	}

}
