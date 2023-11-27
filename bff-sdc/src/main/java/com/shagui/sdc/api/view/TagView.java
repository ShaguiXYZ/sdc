package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.TagDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagView {
    private Integer id;
    private String name;
    private boolean analysisTag;

    public TagView(TagDTO source) {
        BeanUtils.copyProperties(source, this);
    }
}
