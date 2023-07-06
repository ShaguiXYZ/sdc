package com.shagui.sdc.util.documents;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.util.IOUtils;

public class SdcDocumentFactory {
	private SdcDocumentFactory() {
	}

	public static <T extends SdcDocument> T newInstance(String data, Class<T> clazz) throws IOException {
		return newInstance(IOUtils.stringInputStream(data), clazz);
	}

	public static <T extends SdcDocument> T newInstance(InputStream data, Class<T> clazz) throws IOException {
		try {
			Constructor<T> constructor = clazz.getConstructor();
			T document = constructor.newInstance();
			document.input(data);

			return document;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new SdcCustomException(String.format("ERROR instantiating class '%s'.", clazz.getName()), e);
		}
	}
}
