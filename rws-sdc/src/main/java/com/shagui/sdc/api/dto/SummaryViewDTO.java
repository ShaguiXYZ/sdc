package com.shagui.sdc.api.dto;

import com.shagui.sdc.enums.SummaryType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SummaryViewDTO {
    private Integer id;
    private String name;
    private SummaryType type;
    private Float coverage;
}
