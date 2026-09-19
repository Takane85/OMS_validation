package com.ecomeerce.validation.infrastructure.adapter.out.external.order;

import com.ecomeerce.validation.domain.model.Order;

public final class OrderExternalMapper {
	
	private OrderExternalMapper() {}
	
	public static Order toDomain(OrderExternalResponse response) {
		
		return new Order(
                response.orderRef(),
                response.userId(),
                response.canal(),
                response.orderStatus(),
                response.marketPlace(),
                response.giftRegistry(),
                response.items(),
                response.storeName()
        );
		
	}

}
