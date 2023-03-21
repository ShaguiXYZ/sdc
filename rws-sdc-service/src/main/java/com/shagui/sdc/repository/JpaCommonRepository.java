package com.shagui.sdc.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.model.ModelInterface;
import com.shagui.sdc.util.Ctes;

@FunctionalInterface
public interface JpaCommonRepository<R extends JpaRepository<T, K>, T extends ModelInterface<K>, K> {

	R repository();

	default T findById(K id) {
		return repository().findById(id).orElseThrow(() -> new JpaNotFoundException());
	}

	default List<T> findAll() {
		return repository().findAll();
	}

	default List<T> findAll(Iterable<K> keys) {
		return StreamSupport.stream(keys.spliterator(), false).map(id -> repository().findById(id).orElse(null))
				.filter(Objects::nonNull).collect(Collectors.toList());
	}

	default Page<T> findAll(int page) {
		return repository().findAll(getPageable(page));
	}

	default T create(T model) {
		if (model.getId() != null) {
			Optional<T> data = repository().findById(model.getId());

			if (data.isPresent()) {
				throw new RuntimeException();
			}
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
		return getPageable(page, Ctes.JPA.ELEMENTS_BY_PAGE);
	}

	public static Pageable getPageable(int page, int size) {
		return PageRequest.of(page, size);
	}

}
