package com.shagui.sdc.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NumericUtilsTest {

    @Test
    void roundTest() {
        float value = 3.14159f;
        int decimals = 2;
        float expected = 3.14f;
        float result = NumericUtils.round(value, decimals);
        assertEquals(expected, result);
    }

}