package com.ecomeerce.validation.infrastructure.adapter.in.rest.mapper;

import com.ecomeerce.validation.domain.model.Order;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.dto.OrderResponse;

public final class OrderRestMapper {
	
	private OrderRestMapper() {}
	
	public static OrderResponse toResponse(Order order) {

        return new OrderResponse(
                order.orderRef(),
                order.userId(),
                order.canal(),
                order.orderStatus(),
                order.marketPlace(),
                order.giftRegistry(),
                order.itemIds(),
                order.storeName()
        );
    }

}
