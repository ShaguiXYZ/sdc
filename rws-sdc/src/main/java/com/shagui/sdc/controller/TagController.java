package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.TagRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentTagDTO;
import com.shagui.sdc.api.dto.TagDTO;
import com.shagui.sdc.service.TagService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "tags", description = "API to maintain Tags")
public class TagController implements TagRestApi {
    private TagService tagService;

    @Override
    public PageData<TagDTO> tags(Integer page, Integer ps) {
        if (page == null) {
            return tagService.tags();
        } else {
            return tagService.tags(new RequestPageInfo(page, ps));
        }
    }

    @Override
    public PageData<TagDTO> componentTags(int componentId, Integer page, Integer ps) {
        if (page == null) {
            return tagService.componentTags(componentId);
        } else {
            return tagService.componentTags(componentId, new RequestPageInfo(page, ps));
        }
    }

    @Override
    public ComponentTagDTO create(int componentId, String name) {
        return tagService.create(componentId, name);
    }

    @Override
    public void delete(int componentId, String name) {
        tagService.delete(componentId, name);
    }
}
