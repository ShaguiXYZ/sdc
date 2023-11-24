package com.shagui.sdc.util.jpa;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.model.ModelInterface;
import com.shagui.sdc.util.LockHolder;

@FunctionalInterface
public interface JpaCommonRepository<R extends JpaRepository<T, K>, T extends ModelInterface<K>, K> {

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

	default T save(T model) {
		return save(model, false);
	}

	default T saveAndFlush(T model) {
		return save(model, true);
	}

	default List<T> saveAll(Iterable<T> data) {
		return saveAll(data, false);
	}

	default List<T> saveAllAndFlush(Iterable<T> data) {
		return saveAll(data, true);
	}

	default T create(T model) {
		if (model.getId() != null) {
			Optional<T> data = repository().findById(model.getId());

			if (data.isPresent()) {
				throw new JpaNotFoundException();
			}

		}

		return save(model);
	}

	default T update(K id, T model) {
		Optional<T> data = repository().findById(id);

		if (data.isEmpty() || !data.get().getId().equals(model.getId())) {
			throw new JpaNotFoundException();
		}

		return save(model);
	}

	default void delete(T model) {
		if (JpaExpirableData.class.isAssignableFrom(model.getClass())) {
			((JpaExpirableData) model).setExpiryDate(new Date());
			update(model.getId(), model);
		} else {
			this.repository().delete(model);
		}
	}

	@SuppressWarnings("unchecked")
	private T save(T model, boolean flush) {
		if (JpaAutoincrementRepository.class.isAssignableFrom(repository().getClass()) && model.getId() == null) {
			synchronized (LockHolder.AUTOINCREMENT_LOCK) {
				JpaAutoincrementRepository<K> auroincrementRepository = (JpaAutoincrementRepository<K>) repository();
				model.setId(auroincrementRepository.nextId());

				return flush ? repository().saveAndFlush(model) : repository().save(model);
			}
		}

		return flush ? repository().saveAndFlush(model) : repository().save(model);
	}

	private List<T> saveAll(Iterable<T> data, boolean flush) {
		return flush ? repository().saveAllAndFlush(data) : repository().saveAll(data);
	}
}
