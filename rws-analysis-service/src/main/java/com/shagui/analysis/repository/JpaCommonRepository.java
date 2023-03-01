package com.shagui.analysis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.analysis.core.exception.JpaNotFoundException;
import com.shagui.analysis.model.ModelInterface;
import com.shagui.analysis.util.Ctes;

@FunctionalInterface
public interface JpaCommonRepository<R extends JpaRepository<T, K>, T extends ModelInterface<K>, K> {

	R repository();

	default T findById(K id) {
		return repository().findById(id).orElseThrow(() -> new JpaNotFoundException());
	}

	default List<T> findAll() {
		return repository().findAll();
	}

	default Page<T> findAll(int page) {
		return repository().findAll(getPageable(page));
	}

	default T create(T model) {
		if (model.getId() != null) {
			throw new RuntimeException();
		}

		return repository().save(model);
	}

	default T update(K id, T model) {
		Optional<T> data = repository().findById(id);

		if (data.isEmpty() || !data.get().getId().equals(model.getId())) {
			throw new JpaNotFoundException();
		}

		return repository().save(model);
	}

	public static Pageable getPageable(int page) {
		return PageRequest.of(page, Ctes.JPA.ELEMENTS_BY_PAGE);
	}

}
