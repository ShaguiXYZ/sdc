package com.shagui.sdc.service;

import java.util.List;
import java.util.Optional;

public interface DatalistService {
	List<String> availables();

	List<String> datalistValues(String name);

	Optional<String> datalistValue(String dataList, String name);
}
