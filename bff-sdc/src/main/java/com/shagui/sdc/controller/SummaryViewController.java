package com.shagui.sdc.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.SummaryViewApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.SummaryViewView;
import com.shagui.sdc.service.SummaryViewService;
import com.shagui.sdc.util.Mapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class SummaryViewController implements SummaryViewApi {
    private final SummaryViewService summaryViewService;

    @Override
    public PageData<SummaryViewView> filter(String name, Set<String> types, Integer page, Integer ps) {
        return Mapper.parse(summaryViewService.filter(name, types, page, ps), SummaryViewView.class);
    }
}
