package com.shagui.sdc.model.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ComponentTagPk implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8380578041243171512L;

    @Column(name = "component_id", nullable = false)
    private int componentId;

    @Column(name = "tag_id", nullable = false)
    private int tagId;
}
