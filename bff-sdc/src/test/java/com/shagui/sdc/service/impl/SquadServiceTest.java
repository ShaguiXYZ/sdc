package com.shagui.sdc.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.test.utils.DtoDataUtils;

class SquadServiceTest {

	@InjectMocks
	SquadServiceImpl squadService;

	@Mock
	private RwsSdcClient rwsSdcClient;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void squadTest() {
		SquadDTO value = DtoDataUtils.createSquad();
		when(rwsSdcClient.squad(anyInt())).thenReturn(value);
		SquadDTO result = squadService.squad(1);

		assertEquals(result, value);
	}

	@Test
	void squadsTest() {
		PageData<SquadDTO> value = new PageData<SquadDTO>();
		when(rwsSdcClient.squads(anyInt())).thenReturn(value);
		PageData<SquadDTO> result = squadService.squads(1);

		assertEquals(result, value);
	}

	@Test
	void departmentSquadsTest() {
		PageData<SquadDTO> value = new PageData<SquadDTO>();
		when(rwsSdcClient.squadsByDepartment(anyInt(), anyInt())).thenReturn(value);
		PageData<SquadDTO> result = squadService.squadsByDepartment(1, 1);

		assertEquals(result, value);

	}

}
