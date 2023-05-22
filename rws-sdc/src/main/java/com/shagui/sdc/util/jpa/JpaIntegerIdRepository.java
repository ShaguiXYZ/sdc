package com.shagui.sdc.util.jpa;

import java.util.Optional;

public interface JpaIntegerIdRepository extends JpaAuroincrementRepository<Integer> {
	Optional<Integer> maxId();

	@Override
	default Integer nextId() {
		Optional<Integer> maxId = maxId();
		return maxId.isPresent() ? maxId.get() + 1 : 1;
	}
}
