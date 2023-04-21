package com.shagui.sdc.util.validations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class Version implements Comparable<Version> {
	private String str;
	private List<Integer> data;

	public Version(String value) {
		this.str = value;
		this.data = cast(value);
	}

	@Override
	public int compareTo(Version o) {
		int minLen = Math.min(data.size(), o.data.size());
		Optional<Integer> result = IntStream.range(0, minLen).map(i -> data.get(i).compareTo(o.data.get(i))).boxed()
				.filter(c -> c != 0).findFirst();

		return result.isPresent() ? result.get() : 0;
	}

	private List<Integer> cast(String toCast) {
		List<Integer> data = Arrays.asList(toCast.split("\\.")).stream()
				.map(item -> StringUtils.isNumeric(item) ? Integer.valueOf(item) : 0).collect(Collectors.toList());
		return data;
	}

	@Override
	public String toString() {
		return str;
	}
}
