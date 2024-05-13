package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentTagDTO;
import com.shagui.sdc.api.dto.TagDTO;
import com.shagui.sdc.service.TagService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private final RwsSdcClient rwsSdcClient;

    @Override
    public PageData<TagDTO> tags(Integer page, Integer ps) {
        return rwsSdcClient.tags(page, ps);
    }

    @Override
    public PageData<TagDTO> componentTags(int componentId, Integer page, Integer ps) {
        return rwsSdcClient.componentTags(componentId, page, ps);
    }

    @Override
    public ComponentTagDTO create(int componentId, String name) {
        return rwsSdcClient.create(componentId, name);
    }

    @Override
    public void delete(int componentId, String name) {
        rwsSdcClient.deleteComponentTag(componentId, name);
    }
}
