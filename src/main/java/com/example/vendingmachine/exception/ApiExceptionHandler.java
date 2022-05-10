package com.example.vendingmachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(value = {UserNotFoundException.class})
	public ResponseEntity<Object> handleApiRequestException(UserNotFoundException e) {
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = {ProductNotFoundException.class})
	public ResponseEntity<Object> handleApiRequestException(ProductNotFoundException e) {
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = {UsernameConflictException.class})
	public ResponseEntity<Object> handleApiRequestException(UsernameConflictException e) {
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.CONFLICT, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(value = {ProductConflictException.class})
	public ResponseEntity<Object> handleApiRequestException(ProductConflictException e) {
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.CONFLICT, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(value = {UnauthorizedException.class})
	public ResponseEntity<Object> handleApiRequestException(UnauthorizedException e) {
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.UNAUTHORIZED, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = {InvalidAmountException.class})
	public ResponseEntity<Object> handleApiRequestException(InvalidAmountException e) {
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(value = {ProductAmountException.class})
	public ResponseEntity<Object> handleApiRequestException(ProductAmountException e) {
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(value = {UserAmountException.class})
	public ResponseEntity<Object> handleApiRequestException(UserAmountException e) {
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}