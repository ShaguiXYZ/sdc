package com.shagui.sdc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class IOUtils {
	private IOUtils() {
	}

	public static InputStream toInputStream(String data) {
		return new InputStream() {
			private final byte[] msg = data.getBytes();
			private int index = 0;

			@Override
			public int read() {
				if (index >= msg.length) {
					return -1;
				}

				return msg[index++] & 0xFF;
			}
		};
	}

	public static String toString(InputStream inputStream) throws IOException {
		return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
	}

	public static String toStringJava8(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
	}
}
