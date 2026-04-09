package com.shagui.sdc.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BeanUtils {
    private BeanUtils() {
    }

    /**
     * Generates a string representation of an object by reflecting on its declared
     * fields.
     * 
     * This method uses Java reflection to inspect all declared fields of the given
     * object
     * and construct a string containing the class name and field names with their
     * values.
     * 
     * @param obj the object to convert to a string representation. Can be null.
     * @return a string representation in the format "ClassName {field1=value1,
     *         field2=value2, ...}"
     *         or "null" if the input object is null. If a field cannot be accessed
     *         due to
     *         security restrictions, "ACCESS_ERROR" is returned as the field value.
     * 
     * @example
     *          Given an object: Person {name="John", age=30}
     *          Returns: "Person {name=John, age=30}"
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "null";
        }

        // Read all properties of the object using reflection and his values as string
        StringBuilder sb = new StringBuilder();

        sb.append(obj.getClass().getSimpleName()).append(" {");
        Field[] fields = obj.getClass().getDeclaredFields();

        for (var field : fields) {
            field.setAccessible(true);

            try {
                sb.append(field.getName()).append("=").append(field.get(obj)).append(", ");
            } catch (IllegalAccessException e) {
                sb.append(field.getName()).append("=ACCESS_ERROR, ");
            }
        }

        if (fields.length > 0) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
        }

        sb.append("}");

        return sb.toString();
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static List<String> splitAndTrim(String str, int lineLength) {
        return IntStream.range(0, (str.length() + lineLength - 1) / lineLength)
                .mapToObj(i -> str.substring(i * lineLength, Math.min((i + 1) * lineLength, str.length())).trim())
                .collect(Collectors.toList());
    }
}
