package com.shagui.sdc.util.validations.types;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.shagui.sdc.util.SdcStringUtils;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
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
		String version = toCast.replaceAll("[^0-9.]", "");
		return Arrays.stream(version.split("\\."))
				.map(item -> SdcStringUtils.isNumeric(item) ? Integer.valueOf(item) : 0).toList();
	}

	@Override
	public String toString() {
		return str;
	}
}
