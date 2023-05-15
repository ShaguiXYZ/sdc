package com.shagui.sdc.test.utils;

import com.shagui.sdc.model.ModelInterface;

public class ModelMock implements ModelInterface<Integer> {
	private Integer id;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;

	}

}
