package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.StaticRepositoryConfig;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.ComponentUriModel;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.repository.ComponentUriRepository;
import com.shagui.sdc.repository.DepartmentRepository;
import com.shagui.sdc.repository.SquadRepository;
import com.shagui.sdc.service.impl.DataMaintenanceServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.UrlUtils;

class DataMaintenanceServiceImplTest {

	@InjectMocks
	private DataMaintenanceServiceImpl dataMaintenanceService;

	@Mock
	private ComponentRepository componentRepositoryMock;

	@Mock
	private SquadRepository squadRepositoryMock;

	@Mock
	private DepartmentRepository departmentRepositoryMock;

	@Mock
	private ComponentTypeArchitectureRepository componentTypeArchitectureRepositoryMock;

	@Mock
	private ComponentUriRepository componentUriRepositoryMock;

	@Mock
	private StaticRepositoryConfig staticRepositoryConfig;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void updateDepartmentDataTest() {
		Optional<DepartmentModel> departmentOptional = Optional.of(RwsTestUtils.departmentModelMock());
		when(departmentRepositoryMock.findById(anyInt())).thenReturn(departmentOptional);
		when(departmentRepositoryMock.save(any(DepartmentModel.class))).thenReturn(RwsTestUtils.departmentModelMock());

		Optional<SquadModel> squadOptional = Optional.of(RwsTestUtils.squadModelMock());
		when(squadRepositoryMock.findById(anyInt())).thenReturn(squadOptional);
		when(squadRepositoryMock.save(any(SquadModel.class))).thenReturn(RwsTestUtils.squadModelMock());

		DepartmentDTO result = dataMaintenanceService.departmentData(RwsTestUtils.departmentInputMock());

		assertNotNull(result);
	}

	@Test
	void updateNotFoundDepartmentDataTest() {
		Optional<DepartmentModel> departmentOptional = Optional.ofNullable(null);
		when(departmentRepositoryMock.findById(anyInt())).thenReturn(departmentOptional);
		when(departmentRepositoryMock.save(any(DepartmentModel.class))).thenReturn(RwsTestUtils.departmentModelMock());

		Optional<SquadModel> squadOptional = Optional.ofNullable(null);
		when(squadRepositoryMock.findById(anyInt())).thenReturn(squadOptional);
		when(squadRepositoryMock.save(any(SquadModel.class))).thenReturn(RwsTestUtils.squadModelMock());

		DepartmentDTO result = dataMaintenanceService.departmentData(RwsTestUtils.departmentInputMock());

		assertNotNull(result);
	}

	@Test
	void updateDepartmentsDataTest() {
		Optional<DepartmentModel> departmentOptional = Optional.of(RwsTestUtils.departmentModelMock());
		when(departmentRepositoryMock.findById(anyInt())).thenReturn(departmentOptional);
		when(departmentRepositoryMock.save(any(DepartmentModel.class))).thenReturn(RwsTestUtils.departmentModelMock());

		Optional<SquadModel> squadOptional = Optional.of(RwsTestUtils.squadModelMock());
		when(squadRepositoryMock.findById(anyInt())).thenReturn(squadOptional);
		when(squadRepositoryMock.save(any(SquadModel.class))).thenReturn(RwsTestUtils.squadModelMock());

		List<DepartmentDTO> result = dataMaintenanceService.departmentsData(new ArrayList<>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(RwsTestUtils.departmentInputMock());
			}
		});

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	void updateExistingComponentDataTest() {
		Optional<SquadModel> squadOptional = Optional.of(RwsTestUtils.squadModelMock());
		when(squadRepositoryMock.findById(anyInt())).thenReturn(squadOptional);

		Optional<ComponentTypeArchitectureModel> componentTypeArchitectureOptional = Optional
				.of(RwsTestUtils.componentTypeArchitectureModelMock());
		when(componentTypeArchitectureRepositoryMock
				.findByComponentTypeAndArchitectureAndNetworkAndDeploymentTypeAndPlatformAndLanguage(anyString(),
						anyString(), anyString(), anyString(), anyString(), anyString()))
				.thenReturn(componentTypeArchitectureOptional);

		Optional<ComponentModel> componentOptional = Optional.of(RwsTestUtils.componentModelMock());
		when(componentRepositoryMock.findBySquad_IdAndName(anyInt(), anyString())).thenReturn(componentOptional);
		when(componentRepositoryMock.save(any(ComponentModel.class))).thenReturn(RwsTestUtils.componentModelMock());

		StaticRepository.setConfig(staticRepositoryConfig);

		UrlUtils.setConfig(RwsTestUtils.urlUtilsConfig());
		when(staticRepositoryConfig.uris()).thenReturn(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				add(RwsTestUtils.uriModelMock(UriType.GIT));
			}
		});

		when(componentUriRepositoryMock.save(any(ComponentUriModel.class)))
				.thenReturn(RwsTestUtils.componentUriModelMock());

		ComponentDTO dto = dataMaintenanceService.componentData(RwsTestUtils.componentInputMock());

		assertNotNull(dto);
	}

}
