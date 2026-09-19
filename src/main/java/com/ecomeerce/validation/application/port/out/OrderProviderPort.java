package com.ecomeerce.validation.application.port.out;

import java.util.List;

import com.ecomeerce.validation.domain.model.Order;

public interface OrderProviderPort {
	List<Order> findAll();
	List<Order> findByUserId(String userId);

}
