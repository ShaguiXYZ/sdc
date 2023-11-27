package com.shagui.sdc.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentTagDTO;
import com.shagui.sdc.api.dto.TagDTO;
import com.shagui.sdc.core.exception.ExceptionCodes;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTagModel;
import com.shagui.sdc.model.TagModel;
import com.shagui.sdc.model.pk.ComponentTagPk;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.ComponentTagRepository;
import com.shagui.sdc.repository.TagRepository;
import com.shagui.sdc.service.TagService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class TagServiceImpl implements TagService {
    private JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository;
    private JpaCommonRepository<ComponentTagRepository, ComponentTagModel, ComponentTagPk> componentTagRepository;
    private JpaCommonRepository<TagRepository, TagModel, Integer> tagRepository;

    public TagServiceImpl(ComponentRepository componentRepository, ComponentTagRepository componentTagRepository,
            TagRepository tagRepository) {
        this.componentRepository = () -> componentRepository;
        this.componentTagRepository = () -> componentTagRepository;
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
        return tagRepository.repository().findByComponent(componentId).stream().map(Mapper::parse)
                .collect(SdcCollectors.toPageable());
    }

    @Override
    public PageData<TagDTO> componentTags(int componentId, RequestPageInfo pageInfo) {
        Page<TagModel> tags = tagRepository.repository().findByComponent(componentId, pageInfo.getPageable());

        return tags.stream().map(Mapper::parse).collect(SdcCollectors.toPageable(tags));
    }

    @Transactional
    @Override
    public ComponentTagDTO create(int componentId, String name) {
        ComponentModel component = componentRepository.findExistingId(componentId);
        TagModel tag = tagRepository.repository().findByName(name)
                .orElseGet(() -> tagRepository.save(new TagModel(name)));

        ComponentTagPk pk = new ComponentTagPk(componentId, tag.getId());
        return Mapper.parse(componentTagRepository.findById(pk)
                .orElseGet(() -> componentTagRepository.save(new ComponentTagModel(component, tag))));
    }

    @Transactional
    @Override
    public void delete(int componentId, String name) {
        ComponentModel component = componentRepository.findExistingId(componentId);
        TagModel tag = tagRepository.repository().findByName(name)
                .orElseThrow(() -> new JpaNotFoundException(ExceptionCodes.NOT_FOUND_COMPONENT,
                        "no tag %s found for the component %s".formatted(name, component.getName())));

        if (tag.isAnalysisTag()) {
            throw new SdcCustomException("cannot delete analysis tag %s".formatted(name));
        }

        componentTagRepository.repository().deleteByComponentAndTag(component, tag);

        if (componentTagRepository.repository().countByTagId(tag.getId()) == 0) {
            tagRepository.delete(tag);
        }
    }

}
