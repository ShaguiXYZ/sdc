package com.shagui.sdc.util.jpa;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
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

	default T findById(K id) {
		return repository().findById(id).orElseThrow(JpaNotFoundException::new);
	}

	default List<T> findAll() {
		return repository().findAll();
	}

	default List<T> findAll(Iterable<K> keys) {
		return StreamSupport.stream(keys.spliterator(), false).map(id -> repository().findById(id).orElse(null))
				.filter(Objects::nonNull).collect(Collectors.toList());
	}

	default Page<T> findAll(RequestPageInfo pageInfo) {
		return repository().findAll(pageInfo.getPageable());
	}

	@SuppressWarnings("unchecked")
	default T create(T model) {
		if (model.getId() != null) {
			Optional<T> data = repository().findById(model.getId());

			if (data.isPresent()) {
				throw new JpaNotFoundException();
			}

		} else if (JpaAuroincrementRepository.class.isAssignableFrom(repository().getClass())) {
			synchronized (LockHolder.AUTOINCREMENT_LOCK) {
				JpaAuroincrementRepository<K> auroincrementRepository = (JpaAuroincrementRepository<K>) repository();
				model.setId(auroincrementRepository.nextId());

				return repository().save(model);
			}
		}
		
		return repository().save(model);
	}

	default T update(K id, T model) {
		Optional<T> data = repository().findById(id);

		if (!data.isPresent() || !data.get().getId().equals(model.getId())) {
			throw new JpaNotFoundException();
		}

		return repository().save(model);
	}
}
