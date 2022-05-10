package com.example.vendingmachine.exception;

public class UserAmountException extends RuntimeException {

	public UserAmountException() {
		super("Insufficient money amount");
	}

}