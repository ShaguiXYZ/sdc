package com.shagui.sdc.api.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.core.exception.SdcCustomException;

import lombok.Getter;
import lombok.Setter;

class CastToTest {

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void test() {
		CastTo<ClassWithOutEmptyConstructor> castTo = () -> ClassWithOutEmptyConstructor.class;

		assertThrows(SdcCustomException.class, () -> castTo.parse(new SourceClass()));
	}

	class ClassWithOutEmptyConstructor {

	}

	@Getter
	@Setter
	class SourceClass {
		private String prop;

		SourceClass() {
		}
	}
}
