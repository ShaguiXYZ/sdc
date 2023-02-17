package com.shagui.analysis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ExceptionCodes.NOT_FOUND)
public class JpaNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5424297196710502088L;

}
