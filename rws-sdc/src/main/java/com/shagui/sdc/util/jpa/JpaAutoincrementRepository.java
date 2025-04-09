package com.shagui.sdc.util.jpa;

/**
 * Interface for repositories that support auto-incrementing IDs.
 * 
 * @param <K> The type of the ID.
 */
public interface JpaAutoincrementRepository<K> {

	/**
	 * Generates the next auto-incremented ID.
	 * 
	 * @return The next ID of type {@code K}.
	 */
	K nextId();
}
