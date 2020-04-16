package com.example.demo;

public class InvalidCriteriaValueException extends RuntimeException {

	private static final long serialVersionUID = -8864742884664458267L;

	public InvalidCriteriaValueException() {
	}

	public InvalidCriteriaValueException(String message) {
		super(message);
	}

	public InvalidCriteriaValueException(Throwable cause) {
		super(cause);
	}

	public InvalidCriteriaValueException(String message, Throwable cause) {
		super(message, cause);
	}
}
