package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.CompanyDTO;
import com.shagui.sdc.service.CompanyService;

class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    private CompanyController companyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        companyController = new CompanyController(companyService);
    }

    @Test
    void testCompany() {
        int companyId = 1;
        CompanyDTO companyDTO = new CompanyDTO();
        when(companyService.findExistingId(companyId)).thenReturn(companyDTO);

        CompanyDTO result = companyController.company(companyId);

        assertEquals(companyDTO, result);
        verify(companyService, times(1)).findExistingId(companyId);
    }

    @Test
    void testCompanies() {
        PageData<CompanyDTO> pageData = PageData.empty();
        when(companyService.companies()).thenReturn(pageData);

        PageData<CompanyDTO> result = companyController.companies();

        assertEquals(pageData, result);
        verify(companyService, times(1)).companies();
    }

    @Test
    void testPatchAll() {
        CompanyDTO companyDTO1 = new CompanyDTO();
        CompanyDTO companyDTO2 = new CompanyDTO();
        List<CompanyDTO> companies = Arrays.asList(companyDTO1, companyDTO2);
        when(companyService.patchAll(companies)).thenReturn(companies);

        List<CompanyDTO> result = companyController.patchAll(companies);

        assertEquals(companies, result);
        verify(companyService, times(1)).patchAll(companies);
    }
}