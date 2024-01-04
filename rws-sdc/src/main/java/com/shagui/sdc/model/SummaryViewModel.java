package com.shagui.sdc.model;

import org.springframework.data.annotation.Immutable;

import com.shagui.sdc.model.pk.SummaryViewPk;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "summary_view")
public class SummaryViewModel implements ModelInterface<SummaryViewPk> {
    @EmbeddedId
    private SummaryViewPk id;
    private String name;
    private Float coverage;
}
