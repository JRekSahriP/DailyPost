package com.dailypost.exceptions;

public class InvalidPostException extends RuntimeException {
	public InvalidPostException(String message) {super(message);}
	public InvalidPostException() {super("Invalid Post");}
}
