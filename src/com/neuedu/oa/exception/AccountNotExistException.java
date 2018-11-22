package com.neuedu.oa.exception;

public class AccountNotExistException extends OAException{

	public AccountNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountNotExistException(String message) {
		super(message);
	}
	
}
