package com.shagui.sdc.util;

public class NumericUtils {
    private NumericUtils() {
    }

    public static float round(float value, int decimals) {
        return (float) (Math.round(value * Math.pow(10, decimals)) / Math.pow(10, decimals));
    }

}
