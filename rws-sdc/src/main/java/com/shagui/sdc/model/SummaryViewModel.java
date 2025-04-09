package com.shagui.sdc.model;

import org.springframework.data.annotation.Immutable;

import com.shagui.sdc.model.pk.SummaryViewPk;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a summary view entity that provides aggregated data for a specific
 * entity.
 * 
 * This entity is immutable and identified by a composite key.
 * 
 * Relationships:
 * - None explicitly defined, but uses a composite key for identification.
 */
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
