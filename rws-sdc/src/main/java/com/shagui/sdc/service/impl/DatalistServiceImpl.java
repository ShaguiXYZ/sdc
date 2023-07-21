package com.shagui.sdc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.DataListModel;
import com.shagui.sdc.service.DatalistService;

@Service
public class DatalistServiceImpl implements DatalistService {
	@Override
	public List<String> availables() {
		return StaticRepository.datalists().stream().map(DataListModel::getName).collect(Collectors.toList());
	}

	@Override
	public List<String> datalistValues(String name) {
		return StaticRepository.datalists().stream().filter(data -> data.getName().equals(name)).findFirst()
				.map(DataListModel::getValues).orElseGet(ArrayList::new);
	}

}
