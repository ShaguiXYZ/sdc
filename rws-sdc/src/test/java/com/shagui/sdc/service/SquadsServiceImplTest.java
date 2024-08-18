package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.repository.SquadRepository;
import com.shagui.sdc.service.impl.SquadServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;

class SquadsServiceImplTest {

	@InjectMocks
	private SquadServiceImpl service;

	@Mock
	private SquadRepository squadRepositoryMock;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void constructorTest() {
		SquadServiceImpl newService = new SquadServiceImpl(squadRepositoryMock);
		assertNotNull(newService);
	}

	@Test
	void findByTest() {

		Optional<SquadModel> optional = Optional.of(RwsTestUtils.squadModelMock());
		when(squadRepositoryMock.findById(anyInt())).thenReturn(optional);
		SquadDTO result = service.findById(1);
		assertNotNull(result);
	}

	@Test
	void findAllTest() {

		List<SquadModel> value = new ArrayList<SquadModel>();
		value.add(RwsTestUtils.squadModelMock());
		when(squadRepositoryMock.findAll()).thenReturn(value);
		PageData<SquadDTO> result = service.findAll();
		assertNotNull(result);
	}

	@Test
	void findAllPageTest() {

		List<SquadModel> value = new ArrayList<SquadModel>();
		PageImpl<SquadModel> page = new PageImpl<>(value);
		value.add(RwsTestUtils.squadModelMock());
		when(squadRepositoryMock.findAll(any(Pageable.class))).thenReturn(page);
		PageData<SquadDTO> result = service.findAll(new RequestPageInfo(1));
		assertNotNull(result);
	}

	@Test
	void findByDepartmentTest() {

		List<SquadModel> value = new ArrayList<SquadModel>();
		value.add(RwsTestUtils.squadModelMock());
		when(squadRepositoryMock.findByDepartment(any(DepartmentModel.class))).thenReturn(value);
		PageData<SquadDTO> result = service.findByDepartment(1);
		assertNotNull(result);
	}

	@Test
	void findByDepartmentArgumentsTest() {

		List<SquadModel> list = new ArrayList<SquadModel>();
		list.add(RwsTestUtils.squadModelMock());
		Page<SquadModel> value = new PageImpl<SquadModel>(list);
		when(squadRepositoryMock.findByDepartment(any(DepartmentModel.class), any(Pageable.class))).thenReturn(value);
		PageData<SquadDTO> result = service.findByDepartment(1, new RequestPageInfo(1));
		assertNotNull(result);
	}
}
