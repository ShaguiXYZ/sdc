package com.shagui.sdc.util.jpa;

public interface JpaAuroincrementRepository<K> {
	K nextId();
}
