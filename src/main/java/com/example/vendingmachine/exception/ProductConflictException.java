package com.example.vendingmachine.exception;

public class ProductConflictException extends RuntimeException {

	public ProductConflictException(String product) {
		super("Product already exist: " + product);
	}

}