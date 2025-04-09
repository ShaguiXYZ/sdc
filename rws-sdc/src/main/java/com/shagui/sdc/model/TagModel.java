package com.shagui.sdc.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a tag entity that can be associated with multiple components.
 * 
 * Tags are used to categorize or label components.
 * 
 * Relationships:
 * - Many-to-Many with ComponentModel: Tags can be linked to multiple
 * components.
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tags", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class TagModel implements ModelInterface<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Transient
    private String owner;

    @Transient
    private boolean analysisTag;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "component_tags", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "component_id", referencedColumnName = "id"))
    private List<ComponentModel> components = new ArrayList<>();

    public TagModel(String name) {
        this.name = name;
    }

    public TagModel(Integer id, String name, boolean analysisTag, String owner) {
        this.id = id;
        this.name = name;
        this.analysisTag = analysisTag;
        this.owner = owner;
    }
}
