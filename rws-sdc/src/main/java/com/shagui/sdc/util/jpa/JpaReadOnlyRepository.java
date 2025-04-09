package com.shagui.sdc.util.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * Read-only repository interface for JPA entities.
 * 
 * @param <T> The type of the entity.
 * @param <K> The type of the entity's ID.
 */
@NoRepositoryBean
public interface JpaReadOnlyRepository<T, K> extends Repository<T, K> {

    /**
     * Retrieves all entities.
     * 
     * @return A list of all entities.
     */
    List<T> findAll();

    /**
     * Retrieves all entities sorted by the given options.
     * 
     * @param sort The sorting options.
     * @return A list of all entities sorted by the given options.
     */
    List<T> findAll(Sort sort);

    /**
     * Retrieves a page of entities.
     * 
     * @param pageable The pagination information.
     * @return A page of entities.
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Retrieves an entity by its ID.
     * 
     * @param id The ID of the entity.
     * @return An Optional containing the entity if found, or empty if not found.
     */
    Optional<T> findById(K id);

    /**
     * Counts the number of entities.
     * 
     * @return The number of entities.
     */
    long count();
}