package com.bci.test.exception;

public class UserException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String error;

	public static UserException createWith(String error) {
		return new UserException(error);
	}

	private UserException(String username) {
		this.error = username;
	}

	@Override
	public String getMessage() {
		return error;
	}
}
