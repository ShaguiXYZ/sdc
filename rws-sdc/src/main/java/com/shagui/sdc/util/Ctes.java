package com.shagui.sdc.util;

public class Ctes {

	private Ctes() {
	}

	public static class AnalysisServicesTypes {
		public static final String GIT = "GIT"; // = AnalysisType.GIT
		public static final String GIT_JSON = "GIT_JSON"; // = AnalysisType.GIT_JSON
		public static final String GIT_XML = "GIT_XML"; // = AnalysisType.GIT_XML
		public static final String SONAR = "SONAR"; // = AnalysisType.SONAR
		public static final String DEPENDABOT = "DEPENDABOT"; // = DEPENDABOT

		private AnalysisServicesTypes() {
		}
	}

	public static class UriProperties {
		public static final String AUTHORIZATION = "Authorization";

		private UriProperties() {
		}
	}

	public static class ComponentTypeArchitectureMetricConstants {
		public static final String PATH = "PATH";
		public static final String BLOCKER = "BLOCKER";

		private ComponentTypeArchitectureMetricConstants() {
		}
	}

	public static class JPA {
		public static final int ELEMENTS_BY_PAGE = 15;

		private JPA() {
		}
	}

}
