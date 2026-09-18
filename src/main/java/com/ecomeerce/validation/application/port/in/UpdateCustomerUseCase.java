package com.ecomeerce.validation.application.port.in;

import com.ecomeerce.validation.domain.model.Customer;

public interface UpdateCustomerUseCase {
	
	Customer update(String userId, Customer customer);

}
