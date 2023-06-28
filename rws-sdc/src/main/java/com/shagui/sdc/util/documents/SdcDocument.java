package com.shagui.sdc.util.documents;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.shagui.sdc.util.IOUtils;

public interface SdcDocument {
	public void input(InputStream data) throws IOException;
	public Optional<String> fromPath(String path);
	
	default void input(String data) throws IOException {
		input(IOUtils.stringInputStream(data));
	}
}
