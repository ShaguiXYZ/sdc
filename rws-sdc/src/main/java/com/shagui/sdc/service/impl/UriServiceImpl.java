package com.shagui.sdc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.service.UriService;

@Service
public class UriServiceImpl implements UriService {
	@Override
	public List<UriModel> availables() {
		return StaticRepository.uris();
	}
}
