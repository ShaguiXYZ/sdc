package com.shagui.sdc.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.shagui.sdc.api.domain.Range;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.test.utils.RwsTestUtils;

@ExtendWith(MockitoExtension.class)
class ComponentRepositoryTest {

	@Mock
	private EntityManager em;

	@Mock
	private TypedQuery<Object> query;

	@Mock
	ComponentRepository componentRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		when(em.createQuery(anyString(), any())).thenReturn(query);
		when(query.getResultList()).thenReturn(new ArrayList<>());
	}

	@Test
	void filterNotParameterTest() {
		doCallRealMethod().when(componentRepository).filter(any(EntityManager.class), any(), any(), any());
		Page<ComponentModel> page = componentRepository.filter(em, null, null, null);

		assertNotNull(page);
	}

	@Test
	void filterTest() {
		when(query.setParameter(anyString(), any())).thenReturn(query);
		
		doCallRealMethod().when(componentRepository).filter(any(EntityManager.class), anyString(),
				any(SquadModel.class), any(Range.class));
		Page<ComponentModel> page = componentRepository.filter(em, "to find", RwsTestUtils.squadModelMock(),
				new Range(0f, 50f));

		assertNotNull(page);
	}

	@Test
	void filterPageableTest() {
		when(query.setParameter(anyString(), any())).thenReturn(query);
		when(query.getSingleResult()).thenReturn(0l);
		
		doCallRealMethod().when(componentRepository).filter(any(EntityManager.class), anyString(),
				any(SquadModel.class), any(Range.class), any(Pageable.class));
		Page<ComponentModel> page = componentRepository.filter(em, "to find", RwsTestUtils.squadModelMock(),
				new Range(0f, 50f), PageRequest.of(0, 5));

		assertNotNull(page);
	}

}
