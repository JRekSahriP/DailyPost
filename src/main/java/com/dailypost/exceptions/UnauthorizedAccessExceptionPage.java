package com.dailypost.exceptions;

public class UnauthorizedAccessExceptionPage extends RuntimeException {
	public UnauthorizedAccessExceptionPage(String message) {super(message);}
	public UnauthorizedAccessExceptionPage() {super("Unauthorized Access");}
}