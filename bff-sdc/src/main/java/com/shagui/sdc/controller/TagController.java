package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.TagRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.TagView;
import com.shagui.sdc.service.TagService;
import com.shagui.sdc.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "tags", description = "API to maintain Tags")
public class TagController implements TagRestApi {
    private TagService tagService;

    @Override
    public PageData<TagView> tags(Integer page, Integer ps) {
        return Mapper.parse(tagService.tags(page, ps), TagView.class);
    }

    @Override
    public PageData<TagView> componentTags(int componentId, Integer page, Integer ps) {
        return Mapper.parse(tagService.componentTags(componentId, page, ps), TagView.class);
    }
}
