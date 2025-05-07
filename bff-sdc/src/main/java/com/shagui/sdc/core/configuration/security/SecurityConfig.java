package com.shagui.sdc.core.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@Import({ SecurityProperties.class })
public class SecurityConfig {
	private final SecurityProperties securityProperties;

	public SecurityConfig(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Enable CORS and disable CSRF
		http = http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable());
		http.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));

		// Set permissions on endpoints
		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
				.requestMatchers(securityProperties.getApiMatcher()).permitAll());

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
