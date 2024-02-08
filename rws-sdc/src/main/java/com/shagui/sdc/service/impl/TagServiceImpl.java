package com.shagui.sdc.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shagui.sdc.api.client.SecurityClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentTagDTO;
import com.shagui.sdc.api.dto.TagDTO;
import com.shagui.sdc.api.dto.security.UserDTO;
import com.shagui.sdc.core.exception.BadRequestException;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TagServiceImpl implements TagService {
    private final SecurityClient securityClient;
    private JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository;
    private JpaCommonRepository<ComponentTagRepository, ComponentTagModel, ComponentTagPk> componentTagRepository;
    private JpaCommonRepository<TagRepository, TagModel, Integer> tagRepository;

    public TagServiceImpl(SecurityClient securityClient, ComponentRepository componentRepository,
            ComponentTagRepository componentTagRepository,
            TagRepository tagRepository) {
        this.securityClient = securityClient;
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
        String owner = securityClient.authUser().map(UserDTO::getUserName).orElse(null);

        return tagRepository.repository().findByComponent(componentId, owner).stream().map(Mapper::parse)
                .collect(SdcCollectors.toPageable());
    }

    @Override
    public PageData<TagDTO> componentTags(int componentId, RequestPageInfo pageInfo) {
        String owner = securityClient.authUser().map(UserDTO::getUserName).orElse(null);

        Page<TagModel> tags = tagRepository.repository().findByComponent(componentId, owner, pageInfo.getPageable());

        return tags.stream().map(Mapper::parse).collect(SdcCollectors.toPageable(tags));
    }

    @Transactional
    @Override
    public ComponentTagDTO create(int componentId, String name) {
        ComponentModel component = componentRepository.findExistingId(componentId);
        TagModel tag = tagRepository.repository().findByName(name)
                .orElseGet(() -> tagRepository.save(new TagModel(name)));

        securityClient.authUser().ifPresentOrElse(user -> tag.setOwner(user.getUserName()), () -> {
            log.error("no user found");
            throw new BadRequestException();
        });

        ComponentTagPk pk = new ComponentTagPk(componentId, tag.getId());
        return Mapper.parse(componentTagRepository.findById(pk)
                .orElseGet(() -> componentTagRepository.save(new ComponentTagModel(component, tag))));
    }

    @Transactional
    @Override
    public void delete(int componentId, String name) {
        ComponentModel component = componentRepository.findExistingId(componentId);
        UserDTO user = securityClient.authUser().orElseThrow(BadRequestException::new);
        TagModel tag = tagRepository.repository().findByOwnedTag(name, user.getUserName(), componentId)
                .orElseThrow(() -> new JpaNotFoundException(ExceptionCodes.NOT_FOUND_COMPONENT,
                        "no tag %s found for the component %s".formatted(name, component.getName())));

        componentTagRepository.repository().deleteByComponentAndTag(component, tag);

        long tagCount = componentTagRepository.repository().countByTagId(tag.getId());

        if (tagCount == 0) {
            tagRepository.delete(tag);
        }
    }
}
