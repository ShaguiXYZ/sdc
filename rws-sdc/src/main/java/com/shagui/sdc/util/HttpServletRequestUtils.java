package com.shagui.sdc.util;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
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
}