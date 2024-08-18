package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.repository.DepartmentRepository;
import com.shagui.sdc.service.impl.DepartmentServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;

class DepartmentServiceImplTest {

	@InjectMocks
	private DepartmentServiceImpl service;

	@Mock
	private DepartmentRepository departmentRepositoryMock;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void constructorTest() {
		DepartmentServiceImpl newService = new DepartmentServiceImpl(departmentRepositoryMock);
		assertNotNull(newService);
	}

	@Test
	void findByTest() {

		Optional<DepartmentModel> optional = Optional.of(RwsTestUtils.departmentModelMock());
		when(departmentRepositoryMock.findById(anyInt())).thenReturn(optional);
		DepartmentDTO result = service.findById(1);
		assertNotNull(result);
	}

	@Test
	void findAllTest() {

		List<DepartmentModel> value = new ArrayList<DepartmentModel>();
		value.add(RwsTestUtils.departmentModelMock());
		when(departmentRepositoryMock.findAll()).thenReturn(value);
		PageData<DepartmentDTO> result = service.findAll();
		assertNotNull(result);
	}
}
