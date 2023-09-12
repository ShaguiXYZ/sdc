package com.shagui.sdc.util.documents.lib.xml.pom;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.shagui.sdc.util.documents.lib.xml.pom.data.Dependency;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "sdc.document.pom")
public class PomProperties {
	private List<Dependency> deprecatedLibs;
}
