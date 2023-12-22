package com.shagui.sdc.util;

public class Ctes {

	private Ctes() {
	}

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_SESSION_ID = "SID";
	public static final String HEADER_WORKFLOW_ID = "WorkflowId";
	public static final String AUTHORITIES_CLAIM = "authorities";

	public static class AnalysisServicesTypes {
		public static final String CUSTOM = "CUSTOM"; // = AnalysisType.CUSTOM
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

	public static class AnalysisConstants {
		public static final int PRECISION = 2;
		public static final int TREND_DEEP = 10;
		public static final float TREND_HEAT = 0.5f;

		private AnalysisConstants() {
		}
	}

}
