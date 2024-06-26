package com.shagui.sdc.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.SummaryViewDTO;
import com.shagui.sdc.enums.SummaryType;
import com.shagui.sdc.model.SummaryViewModel;
import com.shagui.sdc.model.pk.SummaryViewPk;
import com.shagui.sdc.repository.SummaryViewRepository;
import com.shagui.sdc.service.SummaryViewService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaReadOnlyCommonRepository;
import com.shagui.sdc.util.jpa.JpaUtils;

@Service
public class SummaryViewServiceImpl implements SummaryViewService {
    private final JpaReadOnlyCommonRepository<SummaryViewRepository, SummaryViewModel, SummaryViewPk> summaryViewRepository;

    public SummaryViewServiceImpl(final SummaryViewRepository summaryViewRepository) {
        this.summaryViewRepository = () -> summaryViewRepository;
    }

    @Override
    public PageData<SummaryViewDTO> filter(String name, Set<SummaryType> types) {
        List<SummaryViewModel> models = summaryViewRepository.repository().filter(JpaUtils.contains(name), types,
                Sort.by("name"));

        return models.stream().map(Mapper::parse).collect(SdcCollectors.toPageable());
    }

    @Override
    public PageData<SummaryViewDTO> filter(String name, Set<SummaryType> types, RequestPageInfo pageInfo) {
        Page<SummaryViewModel> models = summaryViewRepository.repository()
                .filter(JpaUtils.contains(name), types, pageInfo.getPageable(Sort.by("name")));

        return models.stream().map(Mapper::parse).collect(SdcCollectors.toPageable(models));
    }

}
