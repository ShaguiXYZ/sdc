package com.shagui.sdc.service;

import java.util.List;

public interface DatalistService {
	List<String> availables();
	List<String> datalistValues(String name);
}
