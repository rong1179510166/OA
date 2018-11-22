package com.neuedu.oa.exception;

public class OAException extends RuntimeException{

	public OAException(String message, Throwable cause) {
		super(message, cause);
	}

	public OAException(String message) {
		super(message);
	}

	
}
