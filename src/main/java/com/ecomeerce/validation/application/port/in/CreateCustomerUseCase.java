package com.ecomeerce.validation.application.port.in;

import com.ecomeerce.validation.domain.model.Customer;

public interface CreateCustomerUseCase {

    Customer create(Customer customer);

}