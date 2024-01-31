package com.shagui.sdc.service;

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
import com.shagui.sdc.model.CompanyModel;
import com.shagui.sdc.repository.CompanyRepository;
import com.shagui.sdc.service.impl.CompanyServiceImpl;
import com.shagui.sdc.test.utils.ReflectUtils;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.Mapper;

class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        companyService = new CompanyServiceImpl(companyRepository);
        ReflectUtils.invoke(Mapper.class, "setConfig", RwsTestUtils.mapperConfig());
    }

    @Test
    void testFindExistingId() {
        int id = 1;
        CompanyModel companyModel = new CompanyModel();
        companyModel.setId(id);
        when(companyRepository.findById(id)).thenReturn(Optional.of(companyModel));

        CompanyDTO result = companyService.findExistingId(id);

        assertEquals(companyModel.getId(), result.getId());
        verify(companyRepository, times(1)).findById(id);
    }

    @Test
    void testCompanies() {
        CompanyModel companyModel1 = new CompanyModel();
        CompanyModel companyModel2 = new CompanyModel();
        List<CompanyModel> companyModels = Arrays.asList(companyModel1, companyModel2);
        when(companyRepository.findAll()).thenReturn(companyModels);

        PageData<CompanyDTO> result = companyService.companies();

        assertEquals(companyModels.size(), result.getPage().size());
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void testPatchAll() {
        CompanyDTO companyDTO1 = new CompanyDTO();
        companyDTO1.setId(1);
        CompanyDTO companyDTO2 = new CompanyDTO();
        companyDTO2.setId(2);

        List<CompanyDTO> companyDTOs = Arrays.asList(companyDTO1, companyDTO2);
        CompanyModel companyModel1 = new CompanyModel(1);
        CompanyModel companyModel2 = new CompanyModel(2);
        List<CompanyModel> companyModels = Arrays.asList(companyModel1, companyModel2);
        when(companyRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(companyRepository.save(any(CompanyModel.class))).thenReturn(companyModel1, companyModel2);

        List<CompanyDTO> result = companyService.patchAll(companyDTOs);

        assertEquals(companyModels.size(), result.size());
        verify(companyRepository, times(companyDTOs.size())).findById(anyInt());
        verify(companyRepository, times(companyDTOs.size())).save(any(CompanyModel.class));
    }
}