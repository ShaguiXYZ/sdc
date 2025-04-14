package com.shagui.sdc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shagui.sdc.util.jpa.JpaExpirableData;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a component entity in the system.
 * This entity is mapped to the "components" table in the database and includes
 * various attributes and relationships to other entities.
 * 
 * <p>
 * The table has a unique constraint on the combination of "squad_id" and
 * "name".
 * </p>
 * 
 * <p>
 * This class includes fields for component metadata, relationships to other
 * entities, and additional attributes such as expiry date, analysis date, and
 * tags.
 * </p>
 * 
 * <p>
 * Relationships:
 * <ul>
 * <li>Many-to-One with {@link ComponentTypeArchitectureModel}</li>
 * <li>Many-to-One with {@link SquadModel}</li>
 * <li>One-to-Many with {@link ComponentPropertyModel}</li>
 * <li>One-to-Many with {@link ComponentUriModel}</li>
 * <li>One-to-Many with {@link ComponentHistoricalCoverageModel}</li>
 * <li>Many-to-Many with {@link TagModel}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Fields:
 * <ul>
 * <li><b>id</b>: Primary key, auto-generated.</li>
 * <li><b>name</b>: Name of the component, cannot be null.</li>
 * <li><b>coverage</b>: Coverage percentage of the component.</li>
 * <li><b>trend</b>: Trend value associated with the component.</li>
 * <li><b>blocked</b>: Indicates whether the component is blocked.</li>
 * <li><b>expiryDate</b>: Expiry date of the component, stored as a timestamp
 * with time zone.</li>
 * <li><b>deleteUser</b>: User who marked the component for deletion, limited to
 * 50 characters.</li>
 * <li><b>analysisDate</b>: Date of the last analysis, stored as a timestamp
 * with time zone.</li>
 * <li><b>componentTypeArchitecture</b>: Reference to the component's type and
 * architecture.</li>
 * <li><b>squad</b>: Reference to the squad associated with the component.</li>
 * <li><b>properties</b>: List of properties associated with the component.</li>
 * <li><b>uris</b>: List of URIs associated with the component.</li>
 * <li><b>historicalCoverage</b>: Historical coverage data for the
 * component.</li>
 * <li><b>tags</b>: List of tags associated with the component.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Annotations:
 * <ul>
 * <li>{@code @Getter} and {@code @Setter}: Automatically generate getter and
 * setter methods.</li>
 * <li>{@code @Entity}: Marks this class as a JPA entity.</li>
 * <li>{@code @Table}: Specifies the table name and unique constraints.</li>
 * <li>{@code @Id}: Marks the primary key field.</li>
 * <li>{@code @GeneratedValue}: Specifies the generation strategy for the
 * primary key.</li>
 * <li>{@code @Column}: Configures column properties such as nullability and
 * length.</li>
 * <li>{@code @Temporal}: Specifies the temporal type for date fields.</li>
 * <li>{@code @ManyToOne}, {@code @OneToMany}, {@code @ManyToMany}: Define
 * relationships with other entities.</li>
 * <li>{@code @JoinColumn} and {@code @JoinTable}: Configure join columns and
 * tables for relationships.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Implements:
 * <ul>
 * <li>{@link ModelInterface} with Integer as the ID type.</li>
 * <li>{@link JpaExpirableData} for handling expiry-related data.</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
@Entity
@Table(name = "components", uniqueConstraints = { @UniqueConstraint(columnNames = { "squad_id", "name" }) })
public class ComponentModel implements ModelInterface<Integer>, JpaExpirableData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	private Float coverage;

	private Float trend;

	private boolean blocked;

	@Column(name = "expiry_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	@Column(name = "delete_user", length = 50)
	private String deleteUser;

	@Column(name = "analysis_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date analysisDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_type_architecture_id", nullable = false)
	private ComponentTypeArchitectureModel componentTypeArchitecture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "squad_id", nullable = false)
	private SquadModel squad;

	@OneToMany(mappedBy = "component", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentPropertyModel> properties = new ArrayList<>();

	@OneToMany(mappedBy = "component", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentUriModel> uris = new ArrayList<>();

	@OneToMany(mappedBy = "component", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentHistoricalCoverageModel> historicalCoverage = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "component_tags", joinColumns = @JoinColumn(name = "component_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
	private List<TagModel> tags = new ArrayList<>();
}
