package com.shagui.sdc.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shagui.sdc.api.domain.Reference;

import lombok.Getter;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SdcCustomException extends RuntimeException {
	@Getter
	private final transient Reference reference;

	public SdcCustomException(String message) {
		super(message);
		this.reference = null;
	}

	public SdcCustomException(String message, Throwable cause) {
		super(message, cause);
		this.reference = null;
	}

	public SdcCustomException(String message, Reference reference) {
		super(message);
		this.reference = reference;
	}

	public SdcCustomException(String message, Throwable cause, Reference reference) {
		super(message, cause);
		this.reference = reference;
	}
}
