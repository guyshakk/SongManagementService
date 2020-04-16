package com.example.demo;

public class EntityNotInDBException extends RuntimeException {

    private static final long serialVersionUID = 505536683636561552L;

    public EntityNotInDBException() {
    }

    public EntityNotInDBException(String message) {
        super(message);
    }

    public EntityNotInDBException(Throwable cause) {
        super(cause);
    }

    public EntityNotInDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public String toString() {
        return " there is no such song in the DB.";

    }
}
