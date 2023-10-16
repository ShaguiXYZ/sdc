package com.shagui.sdc.api.dto.sonar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MeasuresSonarDTOTest {
    @Test
    void measuresTest() {
        MeasuresSonarDTO measuresSonarDTO = new MeasuresSonarDTO();
        List<MeasureSonarDTO> measures = new ArrayList<>();

        MeasureSonarDTO dto1 = new MeasureSonarDTO() {
            {
                setMetric("coverage");
                setValue("80.0");
            }
        };
        measures.add(dto1);

        MeasureSonarDTO dto2 = new MeasureSonarDTO() {
            {
                setMetric("bugs");
                setValue("0");
            }
        };
        measures.add(dto2);

        measuresSonarDTO.setMeasures(measures);

        assertNotNull(measuresSonarDTO.getMeasures());
        assertEquals(2, measuresSonarDTO.getMeasures().size());
        assertEquals("coverage", measuresSonarDTO.getMeasures().get(0).getMetric());
        assertEquals("80.0", measuresSonarDTO.getMeasures().get(0).getValue());
        assertEquals("bugs", measuresSonarDTO.getMeasures().get(1).getMetric());
        assertEquals("0", measuresSonarDTO.getMeasures().get(1).getValue());
    }

}