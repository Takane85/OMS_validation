package com.ecomeerce.validation.application.port.in;

import java.util.List;

import com.ecomeerce.validation.domain.model.Order;

public interface GetCustomerOrdersUseCase {

    List<Order> getOrdersByUserId(String userId);
    
}
