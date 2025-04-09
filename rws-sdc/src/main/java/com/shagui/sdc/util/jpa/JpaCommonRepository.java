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

/**
 * Common repository interface for JPA entities, supporting both read and write
 * operations.
 * 
 * @param <R> The repository type.
 * @param <T> The entity type.
 * @param <K> The ID type.
 */
@FunctionalInterface
public interface JpaCommonRepository<R extends JpaRepository<T, K>, T extends ModelInterface<K>, K> {

	/**
	 * Provides access to the underlying repository.
	 * 
	 * @return The repository instance.
	 */
	R repository();

	/**
	 * Finds an entity by its ID.
	 * 
	 * @param id The ID of the entity.
	 * @return An Optional containing the entity if found, or empty if not found.
	 */
	default Optional<T> findById(K id) {
		return repository().findById(id);
	}

	/**
	 * Finds an existing entity by its ID.
	 * 
	 * @param id The ID of the entity.
	 * @return The entity if found.
	 * @throws JpaNotFoundException if the entity is not found.
	 */
	default T findExistingId(K id) {
		return repository().findById(id).orElseThrow(JpaNotFoundException::new);
	}

	/**
	 * Finds an entity by its ID, or returns the provided default entity if not
	 * found.
	 * 
	 * @param id     The ID of the entity.
	 * @param orData The default entity to return if not found.
	 * @return The found entity or the default entity.
	 */
	default T findByIdOr(K id, T orData) {
		return repository().findById(id).orElse(orData);
	}

	/**
	 * Finds all entities.
	 * 
	 * @return A list of all entities.
	 */
	default List<T> findAll() {
		return repository().findAll();
	}

	/**
	 * Finds all entities by their IDs.
	 * 
	 * @param keys The IDs of the entities.
	 * @return A list of found entities.
	 */
	default List<T> findAll(Iterable<K> keys) {
		return StreamSupport.stream(keys.spliterator(), false).map(id -> repository().findById(id).orElse(null))
				.filter(Objects::nonNull).toList();
	}

	/**
	 * Finds all entities with pagination.
	 * 
	 * @param pageInfo The pagination information.
	 * @return A page of entities.
	 */
	default Page<T> findAll(RequestPageInfo pageInfo) {
		return repository().findAll(pageInfo.getPageable());
	}

	/**
	 * Saves an entity.
	 * 
	 * @param model The entity to save.
	 * @return The saved entity.
	 */
	default T save(T model) {
		return save(model, false);
	}

	/**
	 * Saves and flushes an entity.
	 * 
	 * @param model The entity to save and flush.
	 * @return The saved entity.
	 */
	default T saveAndFlush(T model) {
		return save(model, true);
	}

	/**
	 * Saves multiple entities.
	 * 
	 * @param data The entities to save.
	 * @return A list of saved entities.
	 */
	default List<T> saveAll(Iterable<T> data) {
		return saveAll(data, false);
	}

	/**
	 * Saves and flushes multiple entities.
	 * 
	 * @param data The entities to save and flush.
	 * @return A list of saved entities.
	 */
	default List<T> saveAllAndFlush(Iterable<T> data) {
		return saveAll(data, true);
	}

	/**
	 * Creates a new entity.
	 * 
	 * @param model The entity to create.
	 * @return The created entity.
	 * @throws JpaNotFoundException if the entity already exists.
	 */
	default T create(T model) {
		checkFreeId(model).ifPresent(id -> {
			throw new JpaNotFoundException("Can't create model with id: '%s' because it already exists.".formatted(id));
		});

		return save(model);
	}

	/**
	 * Creates and flushes a new entity.
	 * 
	 * @param model The entity to create and flush.
	 * @return The created entity.
	 * @throws JpaNotFoundException if the entity already exists.
	 */
	default T createAndFlush(T model) {
		checkFreeId(model).ifPresent(id -> {
			throw new JpaNotFoundException("Can't create model with id: '%s' because it already exists.".formatted(id));
		});

		return save(model, true);
	}

	/**
	 * Updates an existing entity.
	 * 
	 * @param id    The ID of the entity to update.
	 * @param model The updated entity.
	 * @return The updated entity.
	 * @throws JpaNotFoundException if the entity does not exist.
	 */
	default T update(@NonNull K id, T model) {
		if (!model.getId().equals(id) || checkFreeId(model).isEmpty()) {
			throw new JpaNotFoundException("Can't update model with id: '%s' because it doesn't exist.".formatted(id));
		}

		return save(model);
	}

	/**
	 * Updates and flushes an existing entity.
	 * 
	 * @param id    The ID of the entity to update.
	 * @param model The updated entity.
	 * @return The updated entity.
	 * @throws JpaNotFoundException if the entity does not exist.
	 */
	default T updateAndFlush(@NonNull K id, T model) {
		if (!model.getId().equals(id) || checkFreeId(model).isEmpty()) {
			throw new JpaNotFoundException("Can't update model with id: '%s' because it doesn't exist.".formatted(id));
		}

		return save(model, true);
	}

	/**
	 * Deletes an entity.
	 * 
	 * @param model The entity to delete.
	 */
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
