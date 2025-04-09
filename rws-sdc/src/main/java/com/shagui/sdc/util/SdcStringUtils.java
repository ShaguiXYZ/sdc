package com.shagui.sdc.util;

import java.util.stream.Stream;

/**
 * The {@code SdcStringUtils} class provides utility methods for validating and
 * manipulating strings.
 * It includes methods for checking if a string matches specific patterns (e.g.,
 * email, name, numeric)
 * and for padding strings to a specified length.
 *
 * <p>
 * This class is implemented as a singleton, with a single instance accessible
 * via the {@code INSTANCE} field.
 */
public class SdcStringUtils {
    public static final SdcStringUtils INSTANCE = new SdcStringUtils();

    private final String EMAIL_REG = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private final String NAME_REG = "^[A-Za-z, çÇñÑáéíóúàèìòùÁÉÍÓÚÀÈÌÒÙüÜãñõÃÑÕâêîôûÂÊÎÔÛ, \\’\\']+$";
    private final String NUMS_REG = "-?\\d+(\\.\\d+)?";
    private final String NUMS_UNSIGNED_REG = "^[0-9]+$";
    private final String TEXT_REG = "^[A-Za-z, 0-9, çÇñÑáéíóúàèìòùÁÉÍÓÚÀÈÌÒÙüÜãñõÃÑÕâêîôûÂÊÎÔÛ, \\+\\/]+$";

    private SdcStringUtils() {
        // Prevent instantiation
    }

    /**
     * Checks if the given string matches the pattern for general text.
     *
     * @param value the string to validate
     * @return {@code true} if the string matches the text pattern, {@code false}
     *         otherwise
     */
    public static boolean isText(String value) {
        return value != null && value.matches(INSTANCE.TEXT_REG);
    }

    /**
     * Checks if the given string matches the pattern for a valid name.
     *
     * @param value the string to validate
     * @return {@code true} if the string matches the name pattern, {@code false}
     *         otherwise
     */
    public static boolean isName(String value) {
        return value != null && value.matches(INSTANCE.NAME_REG);
    }

    /**
     * Checks if the given string represents a numeric value (including negative and
     * decimal numbers).
     *
     * @param value the string to validate
     * @return {@code true} if the string is numeric, {@code false} otherwise
     */
    public static boolean isNumeric(String value) {
        return value != null && value.matches(INSTANCE.NUMS_REG);
    }

    /**
     * Checks if the given string represents an unsigned numeric value (only
     * positive integers).
     *
     * @param value the string to validate
     * @return {@code true} if the string is an unsigned numeric value,
     *         {@code false} otherwise
     */
    public static boolean isNumericUnsigned(String value) {
        return value != null && value.matches(INSTANCE.NUMS_UNSIGNED_REG);
    }

    /**
     * Checks if the given string matches the pattern for a valid email address.
     *
     * @param value the string to validate
     * @return {@code true} if the string matches the email pattern, {@code false}
     *         otherwise
     */
    public static boolean isEmail(String value) {
        return value != null && value.matches(INSTANCE.EMAIL_REG);
    }

    /**
     * Pads the given string on the right with the specified character until it
     * reaches the desired length.
     *
     * @param value  the string to pad
     * @param c      the character to use for padding
     * @param length the desired length of the padded string
     * @return the padded string, or {@code null} if the input string is
     *         {@code null}
     */
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

    /**
     * Pads the given string on the left with the specified character until it
     * reaches the desired length.
     *
     * @param value  the string to pad
     * @param c      the character to use for padding
     * @param length the desired length of the padded string
     * @return the padded string, or {@code null} if the input string is
     *         {@code null}
     */
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
