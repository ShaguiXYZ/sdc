package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;

public interface ComponentService {
	ComponentDTO create(ComponentDTO component);

	ComponentDTO update(Integer id, ComponentDTO component);

	PageData<ComponentDTO> findBySquad(int squadId);

	PageData<ComponentDTO> findBySquad(int squadId, RequestPageInfo pageInfo);
}
