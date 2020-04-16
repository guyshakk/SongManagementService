package com.example.demo;

public class TakenSongIdException extends RuntimeException {

	private static final long serialVersionUID = -8864742884664458267L;

	public TakenSongIdException() {
	}

	public TakenSongIdException(String message) {
		super(message);
	}

	public TakenSongIdException(Throwable cause) {
		super(cause);
	}

	public TakenSongIdException(String message, Throwable cause) {
		super(message, cause);
	}
}
