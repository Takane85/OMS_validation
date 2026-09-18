package com.ecomeerce.validation.application.port.out;

import java.util.Optional;

import com.ecomeerce.validation.domain.model.Customer;

public interface CustomerRepositoryPort {
	
	Customer save(Customer customer);
	Optional<Customer> findByUserId(String userId);
	boolean existByUserId(String userId);
	void deleteByUserId(String userId);

}
