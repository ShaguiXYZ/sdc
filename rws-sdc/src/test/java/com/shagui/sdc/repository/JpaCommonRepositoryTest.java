package com.shagui.sdc.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.test.utils.ModelMock;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

class JpaCommonRepositoryTest {
	@Mock
	private JpaRepository<ModelMock, Integer> jpaRespository;

	@Mock
	Page<ModelMock> mockPAge;

	JpaCommonRepository<JpaRepository<ModelMock, Integer>, ModelMock, Integer> jpaCommonRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		jpaCommonRepository = () -> jpaRespository;
	}

	@Test
	void findAllKeysTest() {
		List<Integer> keys = new ArrayList<>();
		keys.add(1);
		Iterable<ModelMock> result = jpaCommonRepository.findAll(keys);
		assertNotNull(result);
	}

	@Test
	void findAllTest() {

		when(jpaRespository.findAll(any(Pageable.class))).thenReturn(mockPAge);

		Iterable<ModelMock> result = jpaCommonRepository.findAll(RwsTestUtils.requestPageInfo());
		assertNotNull(result);
	}

	@Test
	void createNotFoundTest() {
		Optional<ModelMock> model = Optional.of(new ModelMock() {

			{
				setId(1);
			}
		});
		when(jpaRespository.findById(any())).thenReturn(model);
		assertThrows(JpaNotFoundException.class, () -> jpaCommonRepository.create(model.get()));
	}

	@Test
	void createTest() {
		ModelMock model = new ModelMock();
		when(jpaRespository.save(any(ModelMock.class))).thenReturn(model);
		ModelMock result = jpaCommonRepository.create(model);
		assertNotNull(result);
	}

	@Test
	void updateNotFoundTest() {
		ModelMock model = new ModelMock();
		model.setId(1);
		when(jpaRespository.save(any(ModelMock.class))).thenReturn(model);
		assertThrows(JpaNotFoundException.class, () -> jpaCommonRepository.update(1, model));
	}

	@Test
	void updateTest() {
		Optional<ModelMock> model = Optional.of(new ModelMock() {
			{
				setId(1);
			}
		});
		when(jpaRespository.findById(any())).thenReturn(model);
		when(jpaRespository.save(any(ModelMock.class))).thenReturn(model.get());

		ModelMock result = jpaCommonRepository.update(1, model.get());
		assertNotNull(result);
	}
}
