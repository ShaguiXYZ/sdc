package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.TagDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagView {
    private Integer id;
    private String name;
    private int weight;

    public TagView(TagDTO source) {
        BeanUtils.copyProperties(source, this);
    }
}
