package com.shagui.sdc.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.core.exception.JpaNotFoundException;

class JpaNotFoundExceptionTest {
	
	
	@Test
	void getMessageTest() {
		JpaNotFoundException error = new JpaNotFoundException("message");
		String result = error.getMessage();
		assertEquals(result, "message");
		
	}
	
	@Test
	void getKeyTest() {
		JpaNotFoundException error = new JpaNotFoundException("key","message");
		String result = error.getKey();
		assertEquals(result, "key");
		
	}

}
