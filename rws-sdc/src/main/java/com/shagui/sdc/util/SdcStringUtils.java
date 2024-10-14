package com.shagui.sdc.util;

public class SdcStringUtils {
    public static final SdcStringUtils INSTANCE = new SdcStringUtils();

    private final String EMAIL_REG = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private final String NAME_REG = "^[A-Za-z, çÇñÑáéíóúàèìòùÁÉÍÓÚÀÈÌÒÙüÜãñõÃÑÕâêîôûÂÊÎÔÛ, \\’\\']+$";
    private final String NIF_REG = "^[A-Za-z0-9][0-9]{7,8}[A-Za-z0-9]$";
    private final String NUMS_REG = "-?\\d+(\\.\\d+)?";
    private final String NUMS_UNSIGNED_REG = "^[0-9]+$";
    private final String PRICE_REG = "^\\d+(,\\d{1,2})?$";
    private final String TELEF_REG = "^\\+\\d{1,3}\\d{4,14}$";
    private final String TELEFESP_REG = "^[6789]{1}[0-9]{8}$";
    private final String TEXT_REG = "^[A-Za-z, 0-9, çÇñÑáéíóúàèìòùÁÉÍÓÚÀÈÌÒÙüÜãñõÃÑÕâêîôûÂÊÎÔÛ, \\+\\/]+$";

    private SdcStringUtils() {
    }

    public static boolean isNif(String value) {
        return value != null && value.matches(INSTANCE.NIF_REG);
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

    public static boolean isTelef(String value) {
        return value != null && value.matches(INSTANCE.TELEF_REG);
    }

    public static boolean isTelefEsp(String value) {
        return value != null && value.matches(INSTANCE.TELEFESP_REG);
    }

    public static boolean isPrice(String value) {
        return value != null && value.matches(INSTANCE.PRICE_REG);
    }

    public static boolean isEmail(String value) {
        return value != null && value.matches(INSTANCE.EMAIL_REG);
    }
}
