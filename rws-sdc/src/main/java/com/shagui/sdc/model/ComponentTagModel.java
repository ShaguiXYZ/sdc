package com.shagui.sdc.model;

import com.shagui.sdc.model.pk.ComponentTagPk;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "component_tags")
public class ComponentTagModel implements ModelInterface<ComponentTagPk> {
    @EmbeddedId
    private ComponentTagPk id;

    private String owner;

    @Column(name = "analysis_tag", nullable = false, columnDefinition = "boolean default false")
    private boolean analysisTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("componentId")
    @JoinColumn(name = "component_id", insertable = false, updatable = false)
    private ComponentModel component;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private TagModel tag;

    public ComponentTagModel(ComponentModel component, TagModel tag) {
        this.id = new ComponentTagPk(component.getId(), tag.getId());
        this.component = component;
        this.tag = tag;
    }
}
