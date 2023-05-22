package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.dto.UriDTO;
import com.shagui.sdc.model.UriModel;
import com.shagui.sdc.repository.UriRepository;
import com.shagui.sdc.service.UriService;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class UriServiceImpl implements UriService {
	private JpaCommonRepository<UriRepository, UriModel, Integer> uriRepository;
	
	public UriServiceImpl(UriRepository uriRepository) {
		this.uriRepository = () -> uriRepository;
	}

	@Override
	public UriDTO findById(int uriId) {
		uriRepository.findById(uriId);
		
		return null;
	}

}
