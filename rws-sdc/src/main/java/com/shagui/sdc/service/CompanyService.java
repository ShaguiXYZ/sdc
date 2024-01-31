package com.shagui.sdc.service;

import java.util.List;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.CompanyDTO;

public interface CompanyService {
    CompanyDTO findExistingId(int id);

    PageData<CompanyDTO> companies();

    List<CompanyDTO> patchAll(List<CompanyDTO> companies);
}
