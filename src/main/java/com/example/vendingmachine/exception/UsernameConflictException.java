package com.example.vendingmachine.exception;

public class UsernameConflictException extends RuntimeException {

	public UsernameConflictException(String username) {
		super("Username already exist: " + username);
	}

}