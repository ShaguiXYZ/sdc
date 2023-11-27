package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentTagDTO;
import com.shagui.sdc.api.dto.TagDTO;

public interface TagService {
    public PageData<TagDTO> tags(Integer page, Integer ps);

    public PageData<TagDTO> componentTags(int componentId, Integer page, Integer ps);

    public ComponentTagDTO create(int componentId, String name);

    public void delete(int componentId, String name);
}
