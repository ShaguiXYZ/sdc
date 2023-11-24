package com.shagui.sdc.api.dto;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.view.TagView;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagDTO {
    private Integer id;
    private String name;
    private int weight;

    public TagDTO(TagView source) {
        BeanUtils.copyProperties(source, this);
    }

}
