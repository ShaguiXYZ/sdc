package com.shagui.sdc.util;

public class Ctes {

	private Ctes() {
	}

	public static class ANALYSIS_SERVICES_TYPES {
		public static final String GIT_JSON = "GIT_JSON"; // = AnalysisType.GIT_JSON
		public static final String GIT_XML = "GIT_XML"; // = AnalysisType.GIT_XML
		public static final String SONAR = "SONAR"; // = AnalysisType.SONAR
		public static final String DEPENDABOT = "DEPENDABOT"; // = DEPENDABOT

		private ANALYSIS_SERVICES_TYPES() {
		}
	}

	public static class COMPONENT_PROPERTIES {
		public static final String COMPONENT_ANALYSIS_DATE = "component_analysis_date";

		private COMPONENT_PROPERTIES() {
		}
	}

	public static class URI_PROPERTIES {
		public static final String AUTHORIZATION = "Authorization";

		private URI_PROPERTIES() {
		}
	}

	public static class JPA {
		public static final int ELEMENTS_BY_PAGE = 15;

		private JPA() {
		}
	}

}
