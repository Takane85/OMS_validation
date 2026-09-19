package com.ecomeerce.validation.domain.exception;

public class CustomerAlreadyExistsException extends RuntimeException{
	
	public CustomerAlreadyExistsException(String userId) {
		super("Customer with userId " + userId + " already exists");
	}

}
