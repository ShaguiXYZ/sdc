package com.shagui.analysis.util;

public class Ctes {

	public static interface ANALYSIS_SERVICES_TYPES {
		public static final String XML = "XML"; // = MetricType.XML
		public static final String SONAR = "SONAR"; // = MetricType.SONAR
	}

	public static interface COMPONENT_PROPERTIES {
		public static final String XML_PATH = "xml_path"; 	// path to the pom.xml file from the git uri
															// of the squad the project is associated with
		public static final String PATH = "component_path"; // path to the project root
	}
	
	public static interface JPA {
		public static final int ELEMENTS_BY_PAGE = 15;
	}

}
