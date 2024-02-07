package com.shagui.sdc.util.http;

import java.util.Optional;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The Class HttpServletRequestUtils.
 */
public class HttpServletRequestUtils {
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_SESSION_ID = "SID";

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
	public static Optional<String> getAuthorizationHeader() {
		return getCurrentRequest().map(request -> request.getHeader(HEADER_AUTHORIZATION));
	}

	/**
	 * Gets the SID header from the current {@link HttpServletRequest}.
	 *
	 * @return the SID header or empty String if not present.
	 */
	public static Optional<String> getSIDHeader() {
		return getCurrentRequest().map(request -> request.getHeader(HEADER_SESSION_ID));
	}
}