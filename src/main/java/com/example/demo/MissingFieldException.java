package com.example.demo;

public class MissingFieldException extends RuntimeException {

	private static final long serialVersionUID = -8864742884664458267L;

	public MissingFieldException() {
	}

	public MissingFieldException(String message) {
		super(message);
	}

	public MissingFieldException(Throwable cause) {
		super(cause);
	}

	public MissingFieldException(String message, Throwable cause) {
		super(message, cause);
	}
}
