package com.example.vendingmachine.exception;

public class InvalidAmountException extends RuntimeException {

	public InvalidAmountException(Integer amount) {
		super("Invalid amount cost: " + amount);
	}

}