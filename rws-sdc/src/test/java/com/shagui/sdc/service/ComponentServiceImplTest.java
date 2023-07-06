package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.Range;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.service.impl.ComponentServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;

class ComponentServiceImplTest {

	@InjectMocks
	private ComponentServiceImpl service;

	@Mock
	private EntityManager em;

	@Mock
	private ComponentRepository componentRepositoryMock;

	@Mock
	private ComponentTypeArchitectureRepository componentTypeArchitectureRepositoryMock;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void constructorTest() {
		ComponentServiceImpl service = new ComponentServiceImpl(componentRepositoryMock,
				componentTypeArchitectureRepositoryMock);
		assertNotNull(service);
	}

	@Test
	void findByTest() {
		Optional<ComponentModel> optional = Optional.of(RwsTestUtils.componentModelMock());
		when(componentRepositoryMock.findById(anyInt())).thenReturn(optional);
		ComponentDTO result = service.findBy(1);
		assertNotNull(result);
	}

	@Test
	void filterTest() {
		List<ComponentModel> list = new ArrayList<ComponentModel>();
		list.add(RwsTestUtils.componentModelMock());
		Page<ComponentModel> value = new PageImpl<ComponentModel>(list);
		when(componentRepositoryMock.filter(any(), anyString(), any(SquadModel.class), any(Range.class)))
				.thenReturn(value);

		PageData<ComponentDTO> result = service.filter("filterName", 1, new Range(0f, 1f));

		assertNotNull(result);
	}

	@Test
	void filterWithPageInfoTest() {
		List<ComponentModel> list = new ArrayList<ComponentModel>();
		list.add(RwsTestUtils.componentModelMock());
		Page<ComponentModel> value = new PageImpl<ComponentModel>(list);
		when(componentRepositoryMock.filter(any(), anyString(), any(SquadModel.class), any(Range.class),
				any(Pageable.class))).thenReturn(value);

		PageData<ComponentDTO> result = service.filter("filterName", 1, new Range(0f, 1f), new RequestPageInfo(1));

		assertNotNull(result);
	}

	@Test
	void findBySquadTest() {
		Optional<ComponentModel> optional = Optional.of(RwsTestUtils.componentModelMock());
		when(componentRepositoryMock.findBySquad_IdAndName(anyInt(), anyString())).thenReturn(optional);
		ComponentDTO result = service.findBy(1, "test");
		assertNotNull(result);
	}

	@Test
	void findByJPAExceptionTest() {
		assertThrows(JpaNotFoundException.class, () -> service.findBy(1, "test"));
	}

	@Test
	void squadComponentsTest() {
		List<ComponentModel> value = new ArrayList<ComponentModel>();
		value.add(RwsTestUtils.componentModelMock());
		when(componentRepositoryMock.findBySquad_Id(anyInt(), any(Sort.class))).thenReturn(value);
		PageData<ComponentDTO> result = service.squadComponents(1);
		assertNotNull(result);
	}

	@Test
	void squadComponentsWithPageInfoTest() {
		List<ComponentModel> list = new ArrayList<ComponentModel>();
		list.add(RwsTestUtils.componentModelMock());
		Page<ComponentModel> value = new PageImpl<ComponentModel>(list);
		when(componentRepositoryMock.findBySquad_Id(anyInt(), any(Pageable.class))).thenReturn(value);
		PageData<ComponentDTO> result = service.squadComponents(1, new RequestPageInfo(1));
		assertNotNull(result);
	}

	@Test
	void componentMetricsTest() {
		Optional<ComponentModel> value = Optional.of(RwsTestUtils.componentModelMock());
		when(componentRepositoryMock.findById(anyInt())).thenReturn(value);
		PageData<MetricDTO> result = service.componentMetrics(1);
		assertNotNull(result);
	}
}
