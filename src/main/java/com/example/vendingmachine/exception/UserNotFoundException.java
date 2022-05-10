package com.example.vendingmachine.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
	}

	public UserNotFoundException(String username) {
		super("No username found with username: " + username);
	}

	public UserNotFoundException(Long userId) {
		super("No username found with id: " + userId);
	}

}