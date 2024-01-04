package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.SummaryViewDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SummaryViewView {
    private Integer id;
    private String name;
    private String type;
    private Float coverage;

    public SummaryViewView(SummaryViewDTO source) {
        BeanUtils.copyProperties(source, this);
    }
}
