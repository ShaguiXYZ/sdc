package com.shagui.sdc.service.impl;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.SummaryViewDTO;
import com.shagui.sdc.service.SummaryViewService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SummaryViewServiceImpl implements SummaryViewService {
    private final RwsSdcClient rwsSdcClient;

    @Override
    public PageData<SummaryViewDTO> filter(String name, Set<String> types, Integer page, Integer ps) {
        return rwsSdcClient.summaryFilter(name, types, page, ps);
    }

}
