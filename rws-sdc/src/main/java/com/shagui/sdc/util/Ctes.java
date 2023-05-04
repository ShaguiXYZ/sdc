package com.shagui.sdc.util;

public class Ctes {

	private Ctes() {
	}

	public static class ANALYSIS_SERVICES_TYPES {
		public static final String GIT = "GIT"; // = MetricType.GIT
		public static final String SONAR = "SONAR"; // = MetricType.SONAR

		private ANALYSIS_SERVICES_TYPES() {
		}
	}

	public static class COMPONENT_PROPERTIES {
		public static final String XML_PATH = "xml_path"; // path to the pom.xml file from the git uri
															// of the squad the project is associated with
		public static final String PATH = "component_path"; // path to the project root
		public static final String COMPONENT_OWNER = "component_owner";
		public static final String COMPONENT_REPOSITORY = "component_repository";
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
