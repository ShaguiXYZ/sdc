package com.shagui.sdc.util.validations.types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.shagui.sdc.util.SdcStringUtils;
import com.shagui.sdc.util.validations.Mergeable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NumericMap implements Comparable<NumericMap>, Mergeable<NumericMap> {
    private Map<String, Float> values = new HashMap<>();

    public NumericMap(String value) {
        values.clear();
        String[] data = value.split(";");

        Arrays.stream(data).filter(v -> v.contains("=")).map(v -> v.split("="))
                .filter(v -> v.length == 2 && SdcStringUtils.isNumeric(v[1]))
                .forEach(v -> this.values.put(v[0], Float.parseFloat(v[1])));
    }

    public Map<String, Float> getValues() {
        return values;
    }

    @Override
    public void merge(NumericMap other) {
        if (other == null || other.getValues() == null) {
            return;
        }

        other.getValues().forEach((k, v) -> values.merge(k, v, (v1, v2) -> v1 + v2));
    }

    @Override
    public String toString() {
        return values.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining(";"));
    }

    @Override
    public int compareTo(NumericMap o) {
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
