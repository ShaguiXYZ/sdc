package com.shagui.sdc.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentTagDTO {
    private ComponentDTO component;
    private TagDTO tag;
    private boolean analysisTag;
}
