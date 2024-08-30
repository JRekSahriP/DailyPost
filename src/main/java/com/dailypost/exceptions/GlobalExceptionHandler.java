package com.dailypost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.dailypost.controllers.WebController;

@ControllerAdvice
public class GlobalExceptionHandler {

	//MESSAGES ALERT
	@ExceptionHandler(InvalidPostException.class)
	public ResponseEntity<String> InvalidPostHandler(InvalidPostException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(UnauthorizedAccessExceptionMessage.class)
	public ResponseEntity<String> UnauthorizedAccessHandler(UnauthorizedAccessExceptionMessage e){
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	}
	
	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<String> UsernameAlreadyExistsHandler(UsernameAlreadyExistsException e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<String> HttpRequestMethodNotSupportedHandler(HttpRequestMethodNotSupportedException e){
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Request method not supported: " + e.getMethod());
	} 
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> IllegalArgumentHandler(IllegalArgumentException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> ExceptionHandler(Exception e){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
	
	//REDIRECT PAGE
	@ExceptionHandler(PostNotFoundException.class)
	public ModelAndView PostNotFoundHandler(PostNotFoundException e){
		return WebController.errorPage(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(UnauthorizedAccessExceptionPage.class)
	public ModelAndView UnauthorizedAccessHandler(UnauthorizedAccessExceptionPage e){
		return WebController.errorPage(HttpStatus.FORBIDDEN, e.getMessage());
	}
}
