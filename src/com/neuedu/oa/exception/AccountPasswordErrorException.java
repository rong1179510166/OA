package com.neuedu.oa.exception;

public class AccountPasswordErrorException extends OAException{

	public AccountPasswordErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountPasswordErrorException(String message) {
		super(message);
	}
	
}
