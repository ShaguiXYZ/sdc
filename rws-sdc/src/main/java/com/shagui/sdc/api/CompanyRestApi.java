package com.shagui.sdc.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.CompanyDTO;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface CompanyRestApi {
    @Operation(summary = "Retrieve Company by Id")
    @GetMapping({ "public/company/{companyId}" })
    CompanyDTO company(@PathVariable @Parameter(description = "company identifier") int companyId);

    @Operation(summary = "Retrieve all Companies")
    @GetMapping({ "public/companies" })
    PageData<CompanyDTO> companies();

    @Operation(summary = "Update Companies")
    @PatchMapping("companies")
    @ResponseStatus(HttpStatus.OK)
    List<CompanyDTO> patchAll(@RequestBody List<CompanyDTO> companies);
}
