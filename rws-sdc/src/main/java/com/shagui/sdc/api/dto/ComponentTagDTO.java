package com.shagui.sdc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ComponentTagDTO {
    private ComponentDTO component;
    private TagDTO tag;
    private boolean analysisTag;
}
