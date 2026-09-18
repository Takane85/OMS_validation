package com.ecomeerce.validation.domain.model;

import java.util.List;

public record Customer (
		String userId,
		String firstName,
		String paternalLastName,
		String maternalLastName,
		String email,
		DeliveryAddress deliveryAddress,
		List<String> orders
		) {
	
}
