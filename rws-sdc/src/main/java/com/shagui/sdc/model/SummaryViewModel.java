package com.shagui.sdc.model;

import org.springframework.data.annotation.Immutable;

import com.shagui.sdc.model.pk.SummaryViewPk;
import com.shagui.sdc.util.jpa.ModelInterface;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a summary view model entity mapped to the "summary_view" table in
 * the database.
 * This entity is immutable and uses a composite primary key defined by
 * {@link SummaryViewPk}.
 * 
 * <p>
 * Annotations:
 * </p>
 * <ul>
 * <li>{@code @Getter} - Automatically generates getter methods for all
 * fields.</li>
 * <li>{@code @Setter} - Automatically generates setter methods for all
 * fields.</li>
 * <li>{@code @Entity} - Marks this class as a JPA entity.</li>
 * <li>{@code @Immutable} - Indicates that this entity is read-only and cannot
 * be updated.</li>
 * <li>{@code @Table(name = "summary_view")} - Maps this entity to the
 * "summary_view" table.</li>
 * </ul>
 * 
 * <p>
 * Fields:
 * </p>
 * <ul>
 * <li>{@code id} - The composite primary key of the entity, represented by
 * {@link SummaryViewPk}.</li>
 * <li>{@code name} - The name associated with the summary view. Cannot be
 * null.</li>
 * <li>{@code coverage} - The coverage value associated with the summary view.
 * Can be null.</li>
 * <li>{@code elementType} - The type of element associated with the summary
 * view. Cannot be null.</li>
 * </ul>
 * 
 * <p>
 * Implements:
 * </p>
 * <ul>
 * <li>{@link ModelInterface} with a generic type of {@link SummaryViewPk}.</li>
 * </ul>
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "summary_view")
public class SummaryViewModel implements ModelInterface<SummaryViewPk> {
    @EmbeddedId
    private SummaryViewPk id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "coverage", nullable = true)
    private Float coverage;
}
