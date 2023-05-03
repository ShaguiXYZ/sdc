package com.shagui.sdc.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SdcCustomException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2250115569527848813L;

	public SdcCustomException(String message) {
		super(message);
	}
	
	public SdcCustomException(String message, Throwable cause) {
		super(message, cause);
	}
}
