package com.shagui.sdc.core.configuration.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shagui.sdc.api.client.SecurityClient;
import com.shagui.sdc.core.exception.ApiError;
import com.shagui.sdc.util.Mapper;

import feign.FeignException;

public class AuthorizationFilter extends OncePerRequestFilter {

	private final SecurityClient securityClient;

	public AuthorizationFilter(SecurityClient securityRestApi) {
		this.securityClient = securityRestApi;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			if (null != this.securityClient.authUser()) {
				chain.doFilter(request, response);
			}
		} catch (FeignException e) {
			ApiError apiError = new ApiError(HttpStatus.valueOf(e.status()), e.getLocalizedMessage(), e.getMessage());

			response.setStatus(e.status());
			response.getWriter().write(Mapper.parse(apiError));
		} catch (RuntimeException e) {
			ApiError errorResponse = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(),
					e.getMessage());

			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.getWriter().write(Mapper.parse(errorResponse));
		}
	}
}