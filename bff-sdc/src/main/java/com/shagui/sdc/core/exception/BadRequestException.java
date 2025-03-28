package com.shagui.sdc.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ExceptionCodes.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -5782326437182038227L;

}
