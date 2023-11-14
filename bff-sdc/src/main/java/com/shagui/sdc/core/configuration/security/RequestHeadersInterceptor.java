package com.shagui.sdc.core.configuration.security;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

@Component
class RequestHeadersInterceptor implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate template) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

		if (requestAttributes instanceof ServletRequestAttributes attributes) {
			HttpServletRequest webRequest = attributes.getRequest();
			webRequest.getHeaderNames().asIterator().forEachRemaining(h -> template.header(h, webRequest.getHeader(h)));
		}
	}
}