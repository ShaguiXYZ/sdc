package com.shagui.analysis.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {

	private HttpStatus status;
	private String message;
	private List<String> errors;

	public ApiError() {
		errors = new ArrayList<>();
	}

	public ApiError(HttpStatus status, String message) {
		this(status, message, new ArrayList<>());
	}

	public ApiError(HttpStatus status, String message, String error) {
		this(status, message, Arrays.asList(error));
	}

	public ApiError(HttpStatus status, String message, List<String> errors) {
		super();
		
		this.status = status;
		this.message = message;
		this.errors = new ArrayList<String>(errors);
	}

	public void addError(String error) {
		errors.add(error);
	}
}