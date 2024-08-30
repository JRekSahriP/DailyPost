package com.dailypost.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
	public UsernameAlreadyExistsException(String message) {super(message);}
	public UsernameAlreadyExistsException() {super("Username Already Exists");}
}
