package com.shagui.sdc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.DataListApi;
import com.shagui.sdc.service.DataListService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "dataList", description = "API to maintain Data Lists values")
public class DataListController implements DataListApi {
    private DataListService dataListService;

    @Override
    public List<String> dataLists() {
        return dataListService.dataLists();
    }

    @Override
    public List<String> dataListValues(String datalistName) {
        return dataListService.dataListValues(datalistName);
    }
}
