package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.dto.UriDTO;
import com.shagui.sdc.model.UriModel;
import com.shagui.sdc.repository.UriRepository;
import com.shagui.sdc.service.impl.UriServiceImpl;

class UriServiceImplTest {

	@InjectMocks
	private UriServiceImpl service;

	@Mock
	private UriRepository uriRepositoryMock;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void constructorTest() {

		UriServiceImpl service = new UriServiceImpl(uriRepositoryMock);
		assertNotNull(service);

	}

	@Test
	void findByIdTest() {

		UriModel model = new UriModel();
		model.setId(1);
		Optional<UriModel> optional = Optional.of(model);
		when(uriRepositoryMock.findById(anyInt())).thenReturn(optional);
		UriDTO result = service.findById(1);
		assertNull(result);
	}
	
}