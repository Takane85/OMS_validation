package com.ecomeerce.validation.domain.exception;

public class CustomerNotFoundException extends RuntimeException{
	
	public CustomerNotFoundException(String userId) {
		super("Customer with userId " + userId + "was not found");
	}

}
