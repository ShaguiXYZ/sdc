package com.shagui.sdc.model;

import java.util.ArrayList;
import java.util.List;

import com.shagui.sdc.util.jpa.ModelInterface;

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
 * Represents a Tag entity in the system.
 * This entity is mapped to the "tags" table in the database and includes
 * unique constraints on the "name" column.
 * 
 * <p>
 * A TagModel can be associated with multiple components through a many-to-many
 * relationship, which is managed via the "component_tags" join table.
 * </p>
 * 
 * <p>
 * This class provides constructors for creating TagModel instances with
 * different levels of detail, as well as fields for transient properties
 * that are not persisted in the database.
 * </p>
 * 
 * <p>
 * Annotations:
 * <ul>
 * <li>{@code @Getter} and {@code @Setter} - Automatically generate getter and
 * setter methods for all fields.</li>
 * <li>{@code @Entity} - Marks this class as a JPA entity.</li>
 * <li>{@code @Table} - Specifies the table name and unique constraints.</li>
 * <li>{@code @NoArgsConstructor} - Generates a no-argument constructor.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Fields:
 * <ul>
 * <li>{@code id} - The primary key of the tag, auto-generated.</li>
 * <li>{@code name} - The name of the tag, must be unique and non-null.</li>
 * <li>{@code owner} - A transient field representing the owner of the tag,
 * not persisted in the database.</li>
 * <li>{@code analysisTag} - A transient field indicating if the tag is used
 * for analysis purposes, not persisted in the database.</li>
 * <li>{@code components} - A list of associated components, managed via a
 * many-to-many relationship.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Constructors:
 * <ul>
 * <li>{@code TagModel(String name)} - Creates a TagModel with the specified
 * name.</li>
 * <li>{@code TagModel(Integer id, String name, boolean analysisTag, String owner)}
 * -
 * Creates a TagModel with the specified id, name, analysisTag flag, and
 * owner.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Relationships:
 * <ul>
 * <li>{@code @ManyToMany} - Defines a many-to-many relationship with
 * ComponentModel, using lazy fetching and cascade operations for persist and
 * merge.</li>
 * <li>{@code @JoinTable} - Specifies the join table "component_tags" and the
 * join columns for the relationship.</li>
 * </ul>
 * </p>
 * 
 * @author Shagui
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tags", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class TagModel implements ModelInterface<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Transient
    private String owner;

    @Transient
    private boolean analysisTag;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
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
