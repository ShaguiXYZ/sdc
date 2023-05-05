package com.shagui.sdc.repository;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.test.utils.ModelTest;

class JpaCommonRepositoryTest {
	
	@Mock
	private EntityManager em;

	@Mock
	private ComponentTypeArchitectureRepository componentTypeArchitectureRepositoryMock;
	
	@Mock
	private JpaRepository<ModelTest, Integer> jpaRespository;

	@Autowired
	JpaCommonRepository<JpaRepository<ModelTest, Integer>, ModelTest, Integer> componentRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		
		componentRepository = ()-> jpaRespository;

	}

	@Test
	void findAlltest() {
		
		List<Integer> keys = new ArrayList<>();
		keys.add(1);
		
		List<ModelTest> result = componentRepository.findAll(keys);
		assertNotNull(result);
		
	}

}
