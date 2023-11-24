package com.shagui.sdc.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.TagDTO;
import com.shagui.sdc.model.TagModel;
import com.shagui.sdc.repository.TagRepository;
import com.shagui.sdc.service.TagService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class TagServiceImpl implements TagService {
    private JpaCommonRepository<TagRepository, TagModel, Integer> tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = () -> tagRepository;
    }

    @Override
    public PageData<TagDTO> tags() {
        return tagRepository.repository().findAll().stream().map(Mapper::parse).collect(SdcCollectors.toPageable());
    }

    @Override
    public PageData<TagDTO> tags(RequestPageInfo pageInfo) {
        Page<TagModel> tags = tagRepository.repository().findAll(pageInfo.getPageable());

        return tags.stream().map(Mapper::parse).collect(SdcCollectors.toPageable(tags));
    }

    @Override
    public PageData<TagDTO> componentTags(int componentId) {
        return tagRepository.repository().findByComponents_Id(componentId).stream().map(Mapper::parse)
                .collect(SdcCollectors.toPageable());
    }

    @Override
    public PageData<TagDTO> componentTags(int componentId, RequestPageInfo pageInfo) {
        Page<TagModel> tags = tagRepository.repository().findByComponents_Id(componentId, pageInfo.getPageable());

        return tags.stream().map(Mapper::parse).collect(SdcCollectors.toPageable(tags));
    }

}
