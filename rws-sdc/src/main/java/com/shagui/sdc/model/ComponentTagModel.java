package com.shagui.sdc.model;

import com.shagui.sdc.model.pk.ComponentTagPk;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "component_tags")
public class ComponentTagModel {
    @EmbeddedId
    private ComponentTagPk id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("componentId")
    @JoinColumn(name = "component_id", insertable = false, updatable = false)
    private ComponentModel component;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private TagModel tag;
}
