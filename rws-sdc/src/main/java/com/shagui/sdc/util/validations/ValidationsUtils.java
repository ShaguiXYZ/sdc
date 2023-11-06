package com.shagui.sdc.util.validations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.springframework.util.StringUtils;

public class ValidationsUtils {
    private ValidationsUtils() {
    }

    public static <T> T cast(String toCast, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor(String.class);
            return StringUtils.hasText(toCast) ? constructor.newInstance(toCast) : null;
        } catch (IllegalArgumentException | NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }
}
