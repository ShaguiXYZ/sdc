package com.shagui.sdc.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class JpaNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5424297196710502088L;

	private String key;
	private String message;

	public JpaNotFoundException(String message) {
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
