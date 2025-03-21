package com.shagui.sdc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.DataListModel;
import com.shagui.sdc.service.DatalistService;

@Service
public class DatalistServiceImpl implements DatalistService {
	@Override
	public List<String> availables() {
		return StaticRepository.datalists().stream().map(DataListModel::getName).toList();
	}

	@Override
	public List<String> datalistValues(String name) {
		return StaticRepository.datalists().stream().filter(data -> Objects.equals(data.getName(), name)).findFirst()
				.map(DataListModel::getValues).orElseGet(ArrayList::new);
	}
}
