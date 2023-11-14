package com.shagui.sdc.util.documents.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.util.documents.lib.xml.pom.data.Dependency;

class ModelTest {

//	@Test
//	void pojoModelTest() {
//		assertPojoMethodsFor(Dependency.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
//	}

	@Test
	void equalsTest() {
		Dependency dep1 = new Dependency();
		dep1.setArtifactId("id");

		Dependency dep2 = new Dependency();
		dep2.setArtifactId("id");
		dep2.setGroupId("group");

		assertEquals(true, dep1.equals(dep1));
		assertEquals(true, dep1.equals(dep2));
	}
}
