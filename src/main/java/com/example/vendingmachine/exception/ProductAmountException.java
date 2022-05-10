package com.example.vendingmachine.exception;

public class ProductAmountException extends RuntimeException {

	public ProductAmountException() {
		super("Insufficient products stock");
	}

}