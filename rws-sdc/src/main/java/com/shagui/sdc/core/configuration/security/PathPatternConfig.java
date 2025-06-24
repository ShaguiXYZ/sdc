package com.shagui.sdc.core.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * Configuration for PathPatternParser as a replacement for the deprecated
 * AntPathRequestMatcher
 */
@Configuration
public class PathPatternConfig {
    /**
     * PathPatternParser bean for security route matching.
     * Replaces the deprecated AntPathRequestMatcher functionality.
     *
     * @return configured PathPatternParser
     */
    @Bean
    public PathPatternParser pathPatternParser() {
        PathPatternParser parser = new PathPatternParser();

        // Configurations to replicate the behavior of AntPathRequestMatcher
        parser.setCaseSensitive(true); // Case sensitive by default like AntPathRequestMatcher

        return parser;
    }
}
