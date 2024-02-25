package com.shagui.sdc.core.configuration.security.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

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

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain chain)
			throws ServletException, IOException {
		try {
			if (securityProperties.isAuthorizedRequest(request)) {
				chain.doFilter(request, response);
			} else {
				ApiError apiError = new ApiError(securityProperties.getStatus(), "Unauthorized", "Unauthorized");

				response.setStatus(securityProperties.getStatus().value());
				response.getWriter().write(Mapper.parse(apiError));
			}
		} catch (FeignException e) {
			HttpStatus status = HttpStatus.resolve(e.status());

			if (status == null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}

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
}
