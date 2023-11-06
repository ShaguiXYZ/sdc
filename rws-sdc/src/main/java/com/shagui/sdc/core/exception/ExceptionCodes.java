package com.shagui.sdc.core.exception;

public class ExceptionCodes {
	public static final String BAD_REQUEST = "Exception.BadRequest";
	public static final String NOT_FOUND = "Exception.NotFound";
	public static final String NOT_FOUND_ANALYSIS = "Exception.NotFound.Analysis";
	public static final String NOT_FOUND_COMPONENT = "Exception.NotFound.Component";
	public static final String NOT_FOUND_COMPONENTTYPE_ARCHITECTURE = "Exception.NotFound.ComponentTypeArchitecture";
	public static final String NOT_FOUND_METRIC = "Exception.NotFound.Metric";

	public static final String DEFAULT_EXCEPTION_CODE = BAD_REQUEST;

	private ExceptionCodes() {
	}
}
