package com.shagui.sdc.util;

import java.util.stream.Stream;

public class SdcStringUtils {
    public static final SdcStringUtils INSTANCE = new SdcStringUtils();

    private final String EMAIL_REG = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private final String NAME_REG = "^[A-Za-z, çÇñÑáéíóúàèìòùÁÉÍÓÚÀÈÌÒÙüÜãñõÃÑÕâêîôûÂÊÎÔÛ, \\’\\']+$";
    private final String NUMS_REG = "-?\\d+(\\.\\d+)?";
    private final String NUMS_UNSIGNED_REG = "^[0-9]+$";
    private final String TEXT_REG = "^[A-Za-z, 0-9, çÇñÑáéíóúàèìòùÁÉÍÓÚÀÈÌÒÙüÜãñõÃÑÕâêîôûÂÊÎÔÛ, \\+\\/]+$";

    private SdcStringUtils() {
    }

    public static boolean isText(String value) {
        return value != null && value.matches(INSTANCE.TEXT_REG);
    }

    public static boolean isName(String value) {
        return value != null && value.matches(INSTANCE.NAME_REG);
    }

    public static boolean isNumeric(String value) {
        return value != null && value.matches(INSTANCE.NUMS_REG);
    }

    public static boolean isNumericUnsigned(String value) {
        return value != null && value.matches(INSTANCE.NUMS_UNSIGNED_REG);
    }

    public static boolean isEmail(String value) {
        return value != null && value.matches(INSTANCE.EMAIL_REG);
    }

    public static String padRigth(String value, char c, int length) {
        if (value == null) {
            return null;
        }

        int padLength = length - value.length();

        if (padLength <= 0) {
            return value;
        }

        StringBuilder paddedValue = new StringBuilder(value);

        Stream.generate(() -> c).limit(padLength).forEach(paddedValue::append);

        return paddedValue.toString();
    }

    public static String padLeft(String value, char c, int length) {
        if (value == null) {
            return null;
        }

        int padLength = length - value.length();

        if (padLength <= 0) {
            return value;
        }

        StringBuilder paddedValue = new StringBuilder();

        Stream.generate(() -> c).limit(padLength).forEach(paddedValue::append);
        paddedValue.append(value);

        return paddedValue.toString();
    }
}
