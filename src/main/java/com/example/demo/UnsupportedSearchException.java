package com.example.demo;

public class UnsupportedSearchException extends RuntimeException {
	
	private static final long serialVersionUID = -9033309277716199105L;

	
	public UnsupportedSearchException() {
	}

	public UnsupportedSearchException(String message) {
		super("this search paramenter does not exist : "+message);
	}

	public UnsupportedSearchException(Throwable cause) {
		super(cause);
	}

	public UnsupportedSearchException(String message, Throwable cause) {
		super(message, cause);
	}

}
