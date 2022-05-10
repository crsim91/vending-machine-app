package com.example.vendingmachine.exception;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(Long userId) {
		super("No product found with id: " + userId);
	}

}