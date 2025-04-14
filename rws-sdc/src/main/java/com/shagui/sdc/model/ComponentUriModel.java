package com.shagui.sdc.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.shagui.sdc.model.pk.ComponentUriPk;
import com.shagui.sdc.util.jpa.ModelInterface;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the ComponentUriModel entity which maps to the "component_uris"
 * table in the database.
 * This entity is used to store the relationship between a component and its
 * associated URI.
 * It uses a composite primary key defined by the {@link ComponentUriPk} class.
 *
 * <p>
 * Annotations:
 * </p>
 * <ul>
 * <li>{@code @Getter} - Automatically generates getter methods for all
 * fields.</li>
 * <li>{@code @Setter} - Automatically generates setter methods for all
 * fields.</li>
 * <li>{@code @NoArgsConstructor} - Generates a no-argument constructor for the
 * class.</li>
 * <li>{@code @Entity} - Marks this class as a JPA entity.</li>
 * <li>{@code @Table} - Specifies the table name and unique constraints for the
 * entity.</li>
 * </ul>
 *
 * <p>
 * Fields:
 * </p>
 * <ul>
 * <li>{@code id} - The composite primary key of the entity, represented by
 * {@link ComponentUriPk}.
 * It is annotated with {@code @EmbeddedId} to indicate it is an embedded
 * primary key.</li>
 * <li>{@code component} - A many-to-one relationship with the
 * {@link ComponentModel} entity.
 * It is lazily fetched and mapped using the "component_id" column. The
 * {@code @MapsId} annotation
 * ensures that the "componentId" field in the composite key is mapped to this
 * relationship.</li>
 * </ul>
 *
 * <p>
 * Constraints:
 * </p>
 * <ul>
 * <li>Unique constraint on the combination of "component_id" and "uri_name"
 * columns.</li>
 * </ul>
 *
 * <p>
 * Implements:
 * </p>
 * <ul>
 * <li>{@link ModelInterface} with the generic type {@link ComponentUriPk}.</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "component_uris", uniqueConstraints = { @UniqueConstraint(columnNames = { "component_id", "uri_name" }) })
public class ComponentUriModel implements ModelInterface<ComponentUriPk> {
	@EmbeddedId
	private ComponentUriPk id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("componentId")
	@JoinColumn(name = "component_id", insertable = false, updatable = false)
	private ComponentModel component;
}
