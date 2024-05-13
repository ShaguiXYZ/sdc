package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentTagDTO;
import com.shagui.sdc.api.dto.TagDTO;

public interface TagService {
    PageData<TagDTO> tags(Integer page, Integer ps);

    PageData<TagDTO> componentTags(int componentId, Integer page, Integer ps);

    ComponentTagDTO create(int componentId, String name);

    void delete(int componentId, String name);
}
