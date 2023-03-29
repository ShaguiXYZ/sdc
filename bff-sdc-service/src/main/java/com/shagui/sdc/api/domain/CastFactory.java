package com.shagui.sdc.api.domain;

public class CastFactory {
	private CastFactory() {
	}

	public static <T> CastTo<T> getInstance(Class<T> target) {
		return () -> target;
	}
}
