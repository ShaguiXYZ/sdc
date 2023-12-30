package com.shagui.sdc.service;

import java.util.Set;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.SummaryViewDTO;

public interface SummaryViewService {
    PageData<SummaryViewDTO> filter(String name, Set<String> types, Integer page, Integer ps);
}
