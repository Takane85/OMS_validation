package com.ecomeerce.validation.infrastructure.adapter.in.rest.mapper;

import com.ecomeerce.validation.domain.model.Customer;
import com.ecomeerce.validation.domain.model.DeliveryAddress;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.dto.CustomerRequest;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.dto.CustomerResponse;

public class CustomerRestMapper {

	private CustomerRestMapper() {
	}

	public static Customer toDomain(CustomerRequest request) {

		return new Customer(
				request.userId(), 
				request.firstName(), 
				request.paternalLastName(),
				request.maternalLastName(), 
				request.email(), 
				new DeliveryAddress(request.shippingAddress()), 
				null);
	}
	
	public static CustomerResponse toResponse(Customer customer) {

        String shippingAddress = customer.deliveryAddress() != null
                ? customer.deliveryAddress().shippingAddress()
                : null;

        return new CustomerResponse(
                customer.userId(),
                customer.firstName(),
                customer.paternalLastName(),
                customer.maternalLastName(),
                customer.email(),
                shippingAddress,
                customer.orders()
        );
    }

}
