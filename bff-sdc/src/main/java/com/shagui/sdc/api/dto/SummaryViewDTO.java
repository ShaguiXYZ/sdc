package com.shagui.sdc.api.dto;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.view.SummaryViewView;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SummaryViewDTO {
    private Integer id;
    private String name;
    private String type;
    private Float coverage;

    public SummaryViewDTO(SummaryViewView source) {
        BeanUtils.copyProperties(source, this);
    }
}
