package com.shagui.sdc.util;

public class Ctes {

	public static interface ANALYSIS_SERVICES_TYPES {
		public static final String GIT = "GIT"; // = MetricType.GIT
		public static final String SONAR = "SONAR"; // = MetricType.SONAR
	}

	public static interface COMPONENT_PROPERTIES {
		public static final String XML_PATH = "xml_path"; 	// path to the pom.xml file from the git uri
															// of the squad the project is associated with
		public static final String PATH = "component_path"; // path to the project root
		public static final String COMPONENT_OWNER = "component_owner";
		public static final String COMPONENT_REPOSITORY = "component_repository";
		public static final String COMPONENT_COVERAGE = "component_coverage";
		public static final String COMPONENT_ANALYSIS_DATE = "component_analysis_date";
	}

	public static interface URI_PROPERTIES {
		public static final String AUTHORIZATION = "Authorization";
	}
	
	public static interface JPA {
		public static final int ELEMENTS_BY_PAGE = 15;
	}

}
