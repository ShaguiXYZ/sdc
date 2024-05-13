package com.shagui.sdc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.service.DataListService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DataListServiceImpl implements DataListService {
    private final RwsSdcClient rwsSdcClient;

    @Override
    public List<String> dataLists() {
        return rwsSdcClient.dataLists();
    }

    @Override
    public List<String> dataListValues(String key) {
        return rwsSdcClient.dataListValues(key);
    }
}
