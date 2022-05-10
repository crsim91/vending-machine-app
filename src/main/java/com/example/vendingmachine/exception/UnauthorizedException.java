package com.example.vendingmachine.exception;

public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException() {
		super("No authorized action");
	}
}