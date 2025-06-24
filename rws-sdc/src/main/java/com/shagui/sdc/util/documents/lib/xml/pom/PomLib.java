package com.shagui.sdc.util.documents.lib.xml.pom;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.shagui.sdc.util.documents.data.DocumentServiceDataDTO;
import com.shagui.sdc.util.documents.lib.xml.XmlDocument;
import com.shagui.sdc.util.documents.lib.xml.pom.data.Dependency;

public class PomLib {
	private static PomLibConfig config;

	private PomLib() {
	}

	protected static void setConfig(PomLibConfig config) {
		PomLib.config = config;
	}

	// Metric library fn's
	public static final Function<DocumentServiceDataDTO, String> hasDeprecatedLibrary = serviceData -> {
		List<Dependency> depencencies = ((XmlDocument) serviceData.getDocuemnt()).values("dependencies/dependency",
				Dependency.class);

		Boolean result = depencencies.stream().anyMatch(PomLib.isDeprecatedLibrary);

		return result.toString();
	};

	private static Predicate<Dependency> isDeprecatedLibrary = dependency -> config.properties().getDeprecatedLibs()
			.stream().anyMatch(lib -> lib.equals(dependency));
}
