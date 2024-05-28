package com.shagui.sdc.util.jpa;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

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
		checkFreeId(model).ifPresent(id -> {
			throw new JpaNotFoundException("Can't create model with id: '%s' because it already exists.".formatted(id));
		});

		return save(model);
	}

	default T createAndFlush(T model) {
		checkFreeId(model).ifPresent(id -> {
			throw new JpaNotFoundException("Can't create model with id: '%s' because it already exists.".formatted(id));
		});

		return save(model, true);
	}

	default T update(@NonNull K id, T model) {
		if (!model.getId().equals(id) || checkFreeId(model).isEmpty()) {
			throw new JpaNotFoundException("Can't update model with id: '%s' because it doesn't exist.".formatted(id));
		}

		return save(model);
	}

	default T updateAndFlush(@NonNull K id, T model) {
		if (!model.getId().equals(id) || checkFreeId(model).isEmpty()) {
			throw new JpaNotFoundException("Can't update model with id: '%s' because it doesn't exist.".formatted(id));
		}

		return save(model, true);
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

	private Optional<K> checkFreeId(T model) {
		return Optional.ofNullable(model.getId()).flatMap(id -> {
			if (repository().existsById(id)) {
				return Optional.of(id);
			}

			return Optional.empty();
		});
	}

	private List<T> saveAll(Iterable<T> data, boolean flush) {
		return flush ? repository().saveAllAndFlush(data) : repository().saveAll(data);
	}
}
