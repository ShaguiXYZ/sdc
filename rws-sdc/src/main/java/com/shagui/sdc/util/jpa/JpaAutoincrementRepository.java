package com.shagui.sdc.util.jpa;

public interface JpaAutoincrementRepository<K> {
	K nextId();
}
