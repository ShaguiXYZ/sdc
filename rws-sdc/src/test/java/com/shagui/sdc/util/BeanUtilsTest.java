package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import org.junit.jupiter.api.Test;

class BeanUtilsTest {

    private static class Person {
        private final String name;
        private final int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Test
    void toStringReturnsNullForNullObject() {
        assertEquals("null", BeanUtils.toString(null));
    }

    @Test
    void toStringReflectsDeclaredFields() {
        Person person = new Person("John", 30);

        assertEquals("Person {name=John, age=30}", BeanUtils.toString(person));
    }

    @Test
    void splitAndTrimReturnsCorrectChunks() {
        String input = "abcdefghij";

        List<String> result = BeanUtils.splitAndTrim(input, 4);

        assertEquals(3, result.size());
        assertEquals("abcd", result.get(0));
        assertEquals("efgh", result.get(1));
        assertEquals("ij", result.get(2));
    }

    @Test
    void sleepDoesNotThrowForShortDelay() {
        assertDoesNotThrow(() -> BeanUtils.sleep(1));
    }
}
