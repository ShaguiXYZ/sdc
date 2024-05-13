package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.dto.UriDTO;
import com.shagui.sdc.service.UriService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UriServiceImpl implements UriService {
    private final RwsSdcClient rwsSdcClient;

    @Override
    public UriDTO componentUri(int componentId, String type) {
        return rwsSdcClient.componentUri(componentId, type);
    }
}
