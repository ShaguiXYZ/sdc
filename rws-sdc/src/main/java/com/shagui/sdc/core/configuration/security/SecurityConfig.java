package com.shagui.sdc.core.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.shagui.sdc.api.client.SecurityClient;
import com.shagui.sdc.core.configuration.security.filter.AuthorizationFilter;

@Configuration
@EnableWebSecurity
@Import({ SecurityProperties.class })
public class SecurityConfig {
	private final SecurityClient securityClient;
	private final SecurityProperties securityProperties;

	public SecurityConfig(SecurityProperties securityProperties, SecurityClient securityClient) {
		this.securityProperties = securityProperties;
		this.securityClient = securityClient;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Enable CORS and disable CSRF
		http = http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable());
		http.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));

		// Set permissions on endpoints
		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
				.requestMatchers(securityProperties.getApiMatcher()).permitAll());

		if (securityProperties.isEnabled()) {
			// Add JWT token filter
			http.addFilterAfter(new AuthorizationFilter(this.securityClient),
					UsernamePasswordAuthenticationFilter.class);
		}

		return http.build();
	}

	private CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();

		if (null != securityProperties.getCorsConfiguration()) {
			configurationSource.registerCorsConfiguration("/**", securityProperties.getCorsConfiguration());
		}

		return configurationSource;
	}
}
