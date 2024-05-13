package com.shagui.sdc.util.validations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.springframework.util.StringUtils;

public class ValidationsUtils {
    private ValidationsUtils() {
    }

    public static <T> Optional<T> cast(String toCast, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor(String.class);
            return StringUtils.hasText(toCast) ? Optional.of(constructor.newInstance(toCast)) : Optional.empty();
        } catch (IllegalArgumentException | NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | InvocationTargetException e) {
            return Optional.empty();
        }
    }
}
