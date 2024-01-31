package com.shagui.sdc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.CompanyRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.CompanyDTO;
import com.shagui.sdc.service.CompanyService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "companies", description = "API to maintain Companies")
public class CompanyController implements CompanyRestApi {
    private final CompanyService companyService;

    @Override
    public CompanyDTO company(int companyId) {
        return companyService.findExistingId(companyId);
    }

    @Override
    public PageData<CompanyDTO> companies() {
        return companyService.companies();
    }

    @Override
    public List<CompanyDTO> patchAll(List<CompanyDTO> companies) {
        return companyService.patchAll(companies);
    }
}
