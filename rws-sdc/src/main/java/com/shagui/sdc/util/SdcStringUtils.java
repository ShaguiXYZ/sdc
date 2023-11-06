package com.shagui.sdc.util;

public class SdcStringUtils {
    private SdcStringUtils() {
    }

    public static boolean isNumeric(String value) {
        return value != null && value.matches("-?\\d+(\\.\\d+)?");
    }
}
