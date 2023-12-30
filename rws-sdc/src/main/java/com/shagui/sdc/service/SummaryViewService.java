package com.shagui.sdc.service;

import java.util.Set;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.SummaryViewDTO;
import com.shagui.sdc.enums.SummaryType;

public interface SummaryViewService {
    PageData<SummaryViewDTO> filter(String name, Set<SummaryType> types);

    PageData<SummaryViewDTO> filter(String name, Set<SummaryType> types, RequestPageInfo pageInfo);
}
