package com.shagui.sdc.test.utils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectUtils {

    private ReflectUtils() {
    }

    public static <T> void setAccessible(Class<T> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void setAccessible(T object, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> Object invoke(Class<T> clazz, String methodName, Object... args) {
        try {
            Method method = clazz.getDeclaredMethod(methodName,
                    Arrays.stream(args).map(Object::getClass).toArray(Class[]::new));
            method.setAccessible(true);
            return method.invoke(null, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object invoke(Object object, String methodName, Object... args) {
        try {
            Method method = object.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
