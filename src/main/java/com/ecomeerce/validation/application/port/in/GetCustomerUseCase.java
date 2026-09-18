package com.ecomeerce.validation.application.port.in;

import com.ecomeerce.validation.domain.model.Customer;

public interface GetCustomerUseCase {

    Customer getByUserId(String userId);
}
