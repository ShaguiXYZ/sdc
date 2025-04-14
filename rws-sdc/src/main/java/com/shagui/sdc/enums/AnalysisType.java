package com.shagui.sdc.enums;

/**
 * Enum representing different types of analysis that can be performed.
 * <ul>
 * <li>{@link #REMOTE} - Call remote analysis type metrics.</li>
 * <li>{@link #DEPENDABOT} - Analysis type for Dependabot.</li>
 * <li>{@link #GIT} - Analysis type for Git repositories.</li>
 * <li>{@link #GIT_JSON} - Analysis type for Git repositories with JSON
 * format.</li>
 * <li>{@link #GIT_XML} - Analysis type for Git repositories with XML
 * format.</li>
 * <li>{@link #SONAR} - Analysis type for SonarQube.</li>
 * </ul>
 */
public enum AnalysisType {
	REMOTE, DEPENDABOT, GIT, GIT_JSON, GIT_XML, SONAR
}
