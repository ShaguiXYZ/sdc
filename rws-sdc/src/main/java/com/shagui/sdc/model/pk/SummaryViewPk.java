package com.shagui.sdc.model.pk;

import java.io.Serializable;

import com.shagui.sdc.enums.SummaryType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class SummaryViewPk implements Serializable {
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "element_type")
    private SummaryType type;
}