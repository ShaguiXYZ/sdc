package com.shagui.sdc.core.exception;

import org.springframework.http.HttpStatus;

import com.shagui.sdc.api.domain.Reference;

import lombok.Getter;

public class SdcCustomException extends RuntimeException {
	@Getter
	private final transient HttpStatus httpStatus;

	@Getter
	private final transient Reference reference;

	public SdcCustomException(String message) {
		this(HttpStatus.BAD_REQUEST, message);

	}

	public SdcCustomException(HttpStatus httpStatus, String message) {
		super(message);
		this.reference = null;
		this.httpStatus = httpStatus;
	}

	public SdcCustomException(String message, Throwable cause) {
		this(HttpStatus.BAD_REQUEST, message, cause);

	}

	public SdcCustomException(HttpStatus httpStatus, String message, Throwable cause) {
		super(message, cause);
		this.reference = null;
		this.httpStatus = httpStatus;
	}

	public SdcCustomException(String message, Reference reference) {
		this(HttpStatus.BAD_REQUEST, message, reference);
	}

	public SdcCustomException(HttpStatus httpStatus, String message, Reference reference) {
		super(message);
		this.reference = reference;
		this.httpStatus = httpStatus;
	}

	public SdcCustomException(String message, Throwable cause, Reference reference) {
		this(HttpStatus.BAD_REQUEST, message, cause, reference);

	}

	public SdcCustomException(HttpStatus httpStatus, String message, Throwable cause, Reference reference) {
		super(message, cause);
		this.reference = reference;
		this.httpStatus = httpStatus;
	}
}
