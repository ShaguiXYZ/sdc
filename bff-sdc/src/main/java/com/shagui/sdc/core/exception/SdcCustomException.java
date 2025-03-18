package com.shagui.sdc.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SdcCustomException extends RuntimeException {
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1343723295171758845L;

	public SdcCustomException(String message) {
		super(message);
	}

	public SdcCustomException(String message, Throwable cause) {
		super(message, cause);
	}
}
