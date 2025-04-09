package com.shagui.sdc.model;

/**
 * A generic interface for all model entities, providing methods to get and set
 * the primary key.
 *
 * @param <K> the type of the primary key
 */
public interface ModelInterface<K> {
	K getId();

	void setId(K id);
}
