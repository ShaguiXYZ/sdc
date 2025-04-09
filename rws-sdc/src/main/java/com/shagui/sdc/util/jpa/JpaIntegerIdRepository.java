package com.shagui.sdc.util.jpa;

import java.util.Optional;

/**
 * Repository interface for entities with integer IDs.
 */
public interface JpaIntegerIdRepository extends JpaAutoincrementRepository<Integer> {

	/**
	 * Retrieves the maximum ID currently in use.
	 * 
	 * @return An {@code Optional} containing the maximum ID, or empty if none
	 *         exist.
	 */
	Optional<Integer> maxId();

	@Override
	default Integer nextId() {
		return maxId().map(id -> ++id).orElse(1);
	}
}
