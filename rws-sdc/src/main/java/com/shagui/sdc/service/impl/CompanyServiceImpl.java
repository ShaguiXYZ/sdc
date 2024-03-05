package com.shagui.sdc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.CompanyDTO;
import com.shagui.sdc.model.CompanyModel;
import com.shagui.sdc.repository.CompanyRepository;
import com.shagui.sdc.service.CompanyService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class CompanyServiceImpl implements CompanyService {
    private JpaCommonRepository<CompanyRepository, CompanyModel, Integer> companyRepository;

    public CompanyServiceImpl(final CompanyRepository companyRepository) {
        this.companyRepository = () -> companyRepository;
    }

    @Override
    public CompanyDTO findExistingId(int id) {
        return Mapper.parse(companyRepository.findExistingId(id));
    }

    @Override
    public PageData<CompanyDTO> companies() {
        return companyRepository.findAll()
                .stream()
                .map(Mapper::parse)
                .collect(SdcCollectors.toPageable());
    }

    @Override
    public List<CompanyDTO> patchAll(List<CompanyDTO> companies) {
        return companies.stream()
                .map(this::patchCompany)
                .map(companyRepository::save)
                .map(Mapper::parse)
                .toList();
    }

    private CompanyModel patchCompany(CompanyDTO company) {
        return companyRepository.findById(company.getId())
                .map(cia -> {
                    cia.setName(company.getName());
                    cia.setCodes(company.getCodes());
                    return cia;
                })
                .orElseGet(() -> Mapper.parse(company));
    }
}
