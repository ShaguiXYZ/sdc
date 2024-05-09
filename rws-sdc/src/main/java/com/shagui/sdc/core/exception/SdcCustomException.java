package com.shagui.sdc.core.exception;

import org.springframework.http.HttpStatus;

import com.shagui.sdc.api.domain.Reference;

import lombok.Getter;

public class SdcCustomException extends RuntimeException {
	private static final HttpStatus DEFAULT_STATUS = HttpStatus.BAD_REQUEST;

	@Getter
	private final transient HttpStatus httpStatus;

	@Getter
	private final transient Reference reference;

	public SdcCustomException(String message) {
		this(DEFAULT_STATUS, message);
	}

	public SdcCustomException(HttpStatus httpStatus, String message) {
		this(httpStatus, message, null, null);
	}

	public SdcCustomException(String message, Throwable cause) {
		this(DEFAULT_STATUS, message, cause);
	}

	public SdcCustomException(HttpStatus httpStatus, String message, Throwable cause) {
		this(httpStatus, message, cause, null);
	}

	public SdcCustomException(String message, Reference reference) {
		this(DEFAULT_STATUS, message, reference);
	}

	public SdcCustomException(HttpStatus httpStatus, String message, Reference reference) {
		this(httpStatus, message, null, reference);
	}

	public SdcCustomException(String message, Throwable cause, Reference reference) {
		this(DEFAULT_STATUS, message, cause, reference);
	}

	public SdcCustomException(HttpStatus httpStatus, String message, Throwable cause, Reference reference) {
		super(message, cause);
		this.reference = reference;
		this.httpStatus = defaultHttpStatus(httpStatus);
	}

	private HttpStatus defaultHttpStatus(HttpStatus status) {
		return status == null ? DEFAULT_STATUS : status;
	}
}
