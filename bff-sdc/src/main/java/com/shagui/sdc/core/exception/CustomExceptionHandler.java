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

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler({ Exception.class, RuntimeException.class, SdcCustomException.class })
	ResponseEntity<ApiError> exception(Exception ex) {
		logException(ex);

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ExceptionCodes.DEFAULT_EXCEPTION_CODE,
				ExceptionCodes.DEFAULT_EXCEPTION_CODE);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ BadRequestException.class })
	ResponseEntity<ApiError> exception(BadRequestException ex) {
		logException(ex);

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ExceptionCodes.BAD_REQUEST,
				ExceptionCodes.BAD_REQUEST);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FeignException.class)
	ResponseEntity<ApiError> exception(FeignException ex, WebRequest request) {
		logException(ex);

		ApiError apiError = Mapper.parse(ex);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	private void logException(Exception ex) {
		log.error("{} ------------------------------------>\n", ex.getClass().getName(), ex);
	}
}
