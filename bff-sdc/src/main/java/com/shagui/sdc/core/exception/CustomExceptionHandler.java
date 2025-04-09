package com.shagui.sdc.core.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shagui.sdc.util.Mapper;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for the application.
 * This class intercepts exceptions thrown by controllers and provides
 * appropriate responses to the client.
 * It uses {@link Mapper} to transform Feign exceptions into API errors.
 */
@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles generic exceptions, runtime exceptions, and custom exceptions.
	 *
	 * @param ex the exception to handle
	 * @return a ResponseEntity containing an {@link ApiError} with a BAD_REQUEST
	 *         status
	 */
	@ExceptionHandler({ Exception.class, RuntimeException.class, SdcCustomException.class })
	ResponseEntity<ApiError> exception(Exception ex) {
		logException(ex);

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ExceptionCodes.DEFAULT_EXCEPTION_CODE,
				ExceptionCodes.DEFAULT_EXCEPTION_CODE);
		return buildResponseEntity(apiError);
	}

	/**
	 * Handles {@link BadRequestException} and returns a BAD_REQUEST response.
	 *
	 * @param ex the BadRequestException to handle
	 * @return a ResponseEntity containing an {@link ApiError} with a BAD_REQUEST
	 *         status
	 */
	@ExceptionHandler({ BadRequestException.class })
	ResponseEntity<ApiError> exception(BadRequestException ex) {
		logException(ex);

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ExceptionCodes.BAD_REQUEST,
				ExceptionCodes.BAD_REQUEST);
		return buildResponseEntity(apiError);
	}

	/**
	 * Handles {@link FeignException} and maps it to an {@link ApiError} using
	 * {@link Mapper}.
	 *
	 * @param ex      the FeignException to handle
	 * @param request the current web request
	 * @return a ResponseEntity containing an {@link ApiError} with the appropriate
	 *         status
	 */
	@ExceptionHandler(FeignException.class)
	ResponseEntity<ApiError> exception(FeignException ex, WebRequest request) {
		logException(ex);

		return buildResponseEntity(Mapper.parse(ex));
	}

	/**
	 * Builds a ResponseEntity for the given {@link ApiError}.
	 *
	 * @param apiError the ApiError to include in the response
	 * @return a ResponseEntity containing the ApiError
	 */
	private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Logs the details of the exception.
	 *
	 * @param ex the exception to log
	 */
	private void logException(Throwable ex) {
		log.error("{} ------------------------------------>\n", ex.getClass().getName(), ex);
	}
}
