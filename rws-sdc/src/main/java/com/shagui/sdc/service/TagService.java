package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.TagDTO;

public interface TagService {
    public PageData<TagDTO> tags();

    public PageData<TagDTO> tags(RequestPageInfo pageInfo);

    public PageData<TagDTO> componentTags(int componentId);

    public PageData<TagDTO> componentTags(int componentId, RequestPageInfo pageInfo);
}
