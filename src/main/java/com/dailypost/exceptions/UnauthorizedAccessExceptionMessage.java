package com.dailypost.exceptions;


public class UnauthorizedAccessExceptionMessage extends RuntimeException {
	public UnauthorizedAccessExceptionMessage(String message) {super(message);}
	public UnauthorizedAccessExceptionMessage() {super("Unauthorized Access");}
}
