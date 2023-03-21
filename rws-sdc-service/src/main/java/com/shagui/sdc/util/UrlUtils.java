package com.shagui.sdc.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.shagui.sdc.model.RequestProperiesModel;

public class UrlUtils {
	private UrlUtils() {

	}

	public static URL url(String uri) throws IOException {
		return new URL(uri);
	}

	public static URL url(String uri, List<RequestProperiesModel> properties) throws IOException {
		URL url = UrlUtils.url(uri);
		URLConnection http = url.openConnection();
		
		properties.forEach(property -> {
			http.setRequestProperty(property.getKey(), property.getValue());
		});

		return url;
	}
}
