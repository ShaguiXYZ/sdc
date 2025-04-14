package com.shagui.sdc.model;

import com.shagui.sdc.util.jpa.ModelInterface;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing the configuration for remote metrics.
 * This class maps to the "remote_metrics_config" table in the database.
 * It contains information about the metric and its associated URI.
 * 
 * <p>
 * Fields:
 * <ul>
 * <li><b>id</b>: The unique identifier for the metric configuration.</li>
 * <li><b>metric</b>: The associated metric entity, fetched lazily.</li>
 * <li><b>uri</b>: The URI for the remote metric configuration.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Relationships:
 * <ul>
 * <li><b>metric</b>: One-to-one relationship with {@link MetricModel},
 * joined on the "metric_id" column.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Annotations:
 * <ul>
 * <li><b>@Entity</b>: Marks this class as a JPA entity.</li>
 * <li><b>@Table</b>: Specifies the table name in the database.</li>
 * <li><b>@Id</b>: Marks the primary key field.</li>
 * <li><b>@OneToOne</b>: Defines a one-to-one relationship with another
 * entity.</li>
 * <li><b>@JoinColumn</b>: Specifies the foreign key column for the
 * relationship.</li>
 * <li><b>@Column</b>: Maps a field to a specific column in the database
 * table.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Lombok Annotations:
 * <ul>
 * <li><b>@Getter</b>: Generates getter methods for all fields.</li>
 * <li><b>@Setter</b>: Generates setter methods for all fields.</li>
 * </ul>
 * </p>
 * 
 * @author Shagui
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "remote_metrics_config")
public class RemoteMetricConfigModel implements ModelInterface<Integer> {
    @Id
    @Column(name = "metric_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metric_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MetricModel metric;

    private String uri;
}
