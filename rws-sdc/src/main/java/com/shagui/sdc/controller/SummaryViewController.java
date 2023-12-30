package com.shagui.sdc.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.SummaryViewApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.SummaryViewDTO;
import com.shagui.sdc.enums.SummaryType;
import com.shagui.sdc.service.SummaryViewService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "summary", description = "API to retrieve Summary View")
public class SummaryViewController implements SummaryViewApi {
    private final SummaryViewService summaryViewService;

    @Override
    public PageData<SummaryViewDTO> filter(String name, Set<SummaryType> types, Integer page, Integer ps) {
        if (page == null) {
            return summaryViewService.filter(name, types);
        } else {
            return summaryViewService.filter(name, types, new RequestPageInfo(page, ps));
        }
    }
}
