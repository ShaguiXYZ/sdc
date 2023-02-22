package com.shagui.analysis.core.exception;

public class ExceptionCodes {
	
	public static final String ACCESS_DENIED = "Security.Exception.AccessDenied";
	public static final String BAD_REQUEST = "Exception.BadRequest";
	public static final String NOT_FOUND = "JPA.Exception.NOTFound";
	
	public static final String DEFAULT_EXCEPTION_CODE = BAD_REQUEST;
	
	private ExceptionCodes() {}
}
