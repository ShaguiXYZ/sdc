package com.shagui.analysis.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.analysis.api.dto.UriDTO;
import com.shagui.analysis.model.UriModel;
import com.shagui.analysis.repository.JpaCommonRepository;
import com.shagui.analysis.repository.UriRepository;
import com.shagui.analysis.service.UriService;

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
