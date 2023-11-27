package com.shagui.sdc.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class SdcMapperException extends RuntimeException {
	public SdcMapperException(String message) {
		super(message);
	}

	public SdcMapperException(String message, Throwable cause) {
		super(message, cause);
	}
}
