package com.shagui.sdc.util.jpa;

import java.util.Optional;

public interface JpaIntegerIdRepository extends JpaAutoincrementRepository<Integer> {
	Optional<Integer> maxId();

	@Override
	default Integer nextId() {
		return maxId().map(id -> ++id).orElse(1);
	}
}
