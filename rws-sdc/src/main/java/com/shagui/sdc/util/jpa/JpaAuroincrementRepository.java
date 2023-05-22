package com.shagui.sdc.util.jpa;

public interface JpaAuroincrementRepository<ID> {
	ID nextId();
}
