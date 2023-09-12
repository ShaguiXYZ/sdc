package com.shagui.sdc.util.documents.lib.xml.pom.data;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "dependency")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Dependency implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -374123037204674526L;

	private String groupId;
	private String artifactId;
	private String scope;
	private String version;

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Dependency)) {
			return false;
		}

		Dependency d = (Dependency) o;

		return Objects.equals(d.getArtifactId(), this.artifactId) && Objects.equals(d.getGroupId(), this.groupId)
				&& Objects.equals(d.getScope(), this.scope) && Objects.equals(d.getVersion(), this.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(groupId, artifactId, scope, version);
	}
}
