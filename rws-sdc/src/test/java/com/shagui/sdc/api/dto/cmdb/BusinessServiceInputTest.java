package com.shagui.sdc.api.dto.cmdb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BusinessServiceInputTest {
	@Test
	void constructorAndGettersAndSetters() {
		BusinessServiceInput input = new BusinessServiceInput();
		int id = 1;
		String name = "Business Service";
		input.setId(id);
		input.setName(name);
		assertEquals(id, input.getId());
		assertEquals(name, input.getName());
	}
}