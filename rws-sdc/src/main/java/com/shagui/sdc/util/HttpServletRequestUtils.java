package com.shagui.sdc.util;

import java.util.Optional;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The Class HttpServletRequestUtils.
 */
public class HttpServletRequestUtils {

	private HttpServletRequestUtils() {
	}

	/**
	 * Gets the current {@link HttpServletRequest}.
	 *
	 * @return the current {@link HttpServletRequest} or empty {@link Optional} if
	 *         not present.
	 */
	public static Optional<HttpServletRequest> getCurrentRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

		return Optional.ofNullable(requestAttributes)
				.map(ServletRequestAttributes.class::cast).map(ServletRequestAttributes::getRequest);
	}

	/**
	 * Gets the Authorization header from the current {@link HttpServletRequest}.
	 *
	 * @return the Authorization header or empty String if not present.
	 */
	public static String getAuthorizationHeader() {
		return getCurrentRequest().map(request -> request.getHeader(Ctes.HEADER_AUTHORIZATION)).orElse("");
	}

	/**
	 * Gets the SID header from the current {@link HttpServletRequest}.
	 *
	 * @return the SID header or empty String if not present.
	 */
	public static String getSIDHeader() {
		return getCurrentRequest().map(request -> request.getHeader(Ctes.HEADER_SESSION_ID)).orElse("");
	}

	/**
	 * Gets the SID header from the current {@link HttpServletRequest}.
	 *
	 * @return the SID header or empty String if not present.
	 */
	public static String getWorkfowIdHeader() {
		return getCurrentRequest().map(request -> request.getHeader(Ctes.HEADER_WORKFLOW_ID)).orElse("");
	}
}