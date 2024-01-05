package com.shagui.sdc.core.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shagui.sdc.api.dto.sse.EventFactory;
import com.shagui.sdc.service.SseService;
import com.shagui.sdc.util.HttpServletRequestUtils;
import com.shagui.sdc.util.Mapper;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private SseService sseService;

	@ExceptionHandler({ Exception.class, RuntimeException.class })
	ResponseEntity<ApiError> exception(Exception ex) {
		logException(ex);

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ExceptionCodes.DEFAULT_EXCEPTION_CODE,
				ExceptionCodes.DEFAULT_EXCEPTION_CODE);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ SdcCustomException.class })
	ResponseEntity<ApiError> exception(SdcCustomException ex) {
		String workflowId = HttpServletRequestUtils.getWorkfowIdHeader();

		sseService.emitError(EventFactory.event(workflowId, ex));

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(),
				ExceptionCodes.DEFAULT_EXCEPTION_CODE);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ JpaNotFoundException.class })
	ResponseEntity<ApiError> exception(JpaNotFoundException ex) {
		logException(ex);

		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getKey());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
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
