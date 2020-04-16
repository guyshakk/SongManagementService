package com.example.demo;

public class IncorrectInputException extends RuntimeException {

	private static final long serialVersionUID = -8864742884664458267L;

	public IncorrectInputException() {
	}

	public IncorrectInputException(String message) {
		super(message);
	}

	public IncorrectInputException(Throwable cause) {
		super(cause);
	}

	public IncorrectInputException(String message, Throwable cause) {
		super(message, cause);
	}
}
