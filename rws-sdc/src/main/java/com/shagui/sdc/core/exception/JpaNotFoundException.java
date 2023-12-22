package com.shagui.sdc.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class JpaNotFoundException extends RuntimeException {
	private final String key;
	private final String message;

	public JpaNotFoundException() {
		this.key = null;
		this.message = null;
	}

	public JpaNotFoundException(String message) {
		this.key = null;
		this.message = message;
	}

	public JpaNotFoundException(String key, String message) {
		this.key = key;
		this.message = message;
	}

	public String getKey() {
		return StringUtils.hasText(key) ? key : ExceptionCodes.NOT_FOUND;
	}

	@Override
	public String getMessage() {
		return StringUtils.hasText(message) ? message : super.getMessage();
	}
}
