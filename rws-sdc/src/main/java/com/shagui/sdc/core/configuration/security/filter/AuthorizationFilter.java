package com.shagui.sdc.core.configuration.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shagui.sdc.api.client.SecurityClient;
import com.shagui.sdc.core.configuration.security.SecurityProperties;
import com.shagui.sdc.core.exception.ApiError;
import com.shagui.sdc.util.Mapper;

import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
	private final SecurityProperties securityProperties;
	private final SecurityClient securityClient;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain chain)
			throws ServletException, IOException {
		try {
			if (isPublicRequest(securityProperties.publicRegex(), request) || null != this.securityClient.authUser()) {
				chain.doFilter(request, response);
			}
		} catch (FeignException e) {
			HttpStatus status = Arrays.asList(HttpStatus.values()).stream()
					.filter(st -> st.value() == e.status()).findFirst()
					.orElse(HttpStatus.INTERNAL_SERVER_ERROR);

			ApiError apiError = new ApiError(status, e.getLocalizedMessage(), e.getMessage());

			response.setStatus(status.value());
			response.getWriter().write(Mapper.parse(apiError));
		} catch (RuntimeException e) {
			ApiError errorResponse = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(),
					e.getMessage());

			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.getWriter().write(Mapper.parse(errorResponse));
		}
	}

	private boolean isPublicRequest(String[] regex, HttpServletRequest request) {
		return Arrays.stream(regex)
				.anyMatch(exp -> AntPathRequestMatcher.antMatcher(exp).matches(request));
	}
}