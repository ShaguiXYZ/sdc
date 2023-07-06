package com.shagui.sdc.util;

import java.io.InputStream;

public class IOUtils {
	private IOUtils() {}
	
	public static InputStream stringInputStream(String data) {
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


}
