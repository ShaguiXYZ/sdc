package com.shagui.sdc.util.validations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NumericMap implements Comparable<NumericMap> {
    private Map<String, Float> values = new HashMap<>();

    public NumericMap(String value) {
        values.clear();
        String[] data = value.split(";");

        Arrays.stream(data).filter(v -> v.contains("=")).map(v -> v.split("="))
                .filter(v -> v.length == 2 && StringUtils.isNumeric(v[1]))
                .forEach(v -> this.values.put(v[0], Float.parseFloat(v[1])));
    }

    public Map<String, Float> getValues() {
        return values;
    }

    public void merge(Map<String, Float> other) {
        values.forEach((k, v) -> other.merge(k, v, (v1, v2) -> v1 + v2));
    }

    @Override
    public int compareTo(NumericMap o) {
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
