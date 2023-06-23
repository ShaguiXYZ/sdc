package com.shagui.sdc.util;

public class Ctes {

	private Ctes() {
	}

	public static class ANALYSIS_SERVICES_TYPES {
		public static final String GIT_JSON = "GIT_JSON"; // = AnalysisType.GIT_JSON
		public static final String GIT_XML = "GIT_XML"; // = AnalysisType.GIT_XML
		public static final String SONAR = "SONAR"; // = AnalysisType.SONAR

		private ANALYSIS_SERVICES_TYPES() {
		}
	}

	public static class COMPONENT_PROPERTIES {
		public static final String XML_PATH = "xml_path"; // path to the pom.xml file from the git uri
		public static final String JSON_PATH = "json_path"; // path to the package.json file from the git uri
															// of the squad the project is associated with
		public static final String COMPONENT_OWNER = "organization";
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
