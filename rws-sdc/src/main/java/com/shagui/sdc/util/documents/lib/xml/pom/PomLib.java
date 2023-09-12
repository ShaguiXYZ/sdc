package com.shagui.sdc.util.documents.lib.xml.pom;

import java.util.List;
import java.util.function.Function;

import com.shagui.sdc.util.documents.data.ServiceData;
import com.shagui.sdc.util.documents.lib.xml.XmlDocument;
import com.shagui.sdc.util.documents.lib.xml.pom.data.Dependency;

public class PomLib {
	private static PomLibConfig config;

	private PomLib() {
	}

	public static void setConfig(PomLibConfig config) {
		PomLib.config = config;
	}

	// Metric library fn's
	public static final Function<ServiceData, String> hasDeprecatedLibrary = serviceData -> {
		List<Dependency> depencencies = ((XmlDocument) serviceData.getDocuemnt()).values("dependencies/dependency",
				Dependency.class);

		Boolean result = depencencies.stream()
				.anyMatch(data -> config.properties().getDeprecatedLibs().stream().anyMatch(lib -> lib.equals(data)));

		return result.toString();
	};
}
