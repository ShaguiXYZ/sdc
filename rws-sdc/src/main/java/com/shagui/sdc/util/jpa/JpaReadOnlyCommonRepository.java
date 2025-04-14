package com.shagui.sdc.util.jpa;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;

import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.core.exception.JpaNotFoundException;

/**
 * Common read-only repository interface for JPA entities.
 * 
 * @param <R> The repository type.
 * @param <T> The entity type.
 * @param <K> The ID type.
 */
@FunctionalInterface
public interface JpaReadOnlyCommonRepository<R extends JpaReadOnlyRepository<T, K>, T extends ModelInterface<K>, K> {

	/**
	 * Provides access to the underlying repository.
	 * 
	 * @return The repository instance.
	 */
	R repository();

	default Optional<T> findById(K id) {
		return repository().findById(id);
	}

	default T findExistingId(K id) {
		return repository().findById(id).orElseThrow(JpaNotFoundException::new);
	}

	default T findByIdOr(K id, T orData) {
		return repository().findById(id).orElse(orData);
	}

	default List<T> findAll() {
		return repository().findAll();
	}

	default List<T> findAll(Iterable<K> keys) {
		return StreamSupport.stream(keys.spliterator(), false).map(id -> repository().findById(id).orElse(null))
				.filter(Objects::nonNull).toList();
	}

	default Page<T> findAll(RequestPageInfo pageInfo) {
		return repository().findAll(pageInfo.getPageable());
	}
}
