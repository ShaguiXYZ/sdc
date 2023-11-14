package com.shagui.sdc.util.documents.lib.xml.pom.data;

import java.io.Serializable;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

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

		// artifactId is mandatory the rest of the fields are only compared if the
		// object property has a value
		return Objects.equals(this.artifactId, d.getArtifactId()) && equalsNotNull(this.groupId, d.getGroupId())
				&& equalsNotNull(this.scope, d.getScope()) && equalsNotNull(this.version, d.getVersion());
	}

	@Override
	public int hashCode() {
		return Objects.hash(groupId, artifactId, scope, version);
	}

	private boolean equalsNotNull(Object a, Object b) {
		return a == null || a.equals(b);
	}
}
