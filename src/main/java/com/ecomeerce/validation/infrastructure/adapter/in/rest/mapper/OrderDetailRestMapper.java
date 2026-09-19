package com.ecomeerce.validation.infrastructure.adapter.in.rest.mapper;

import com.ecomeerce.validation.domain.model.Item;
import com.ecomeerce.validation.domain.model.OrderDetail;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.dto.ItemResponse;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.dto.OrderDetailResponse;

public final class OrderDetailRestMapper {
	
	private OrderDetailRestMapper() {}
	
	public static OrderDetailResponse toResponse(OrderDetail order) {
		return new OrderDetailResponse(
				order.orderRef(),
                order.userId(),
                order.canal(),
                order.orderStatus(),
                order.marketPlace(),
                order.giftRegistry(),
                order.storeName(),
                order.items()
                        .stream()
                        .map(OrderDetailRestMapper::toItemResponse)
                        .toList()
				);
	}
	
	private static ItemResponse toItemResponse(Item item) {
		return new ItemResponse(
				item.itemId(),
                item.skuId(),
                item.quantity(),
                item.displayName(),
                item.deliveryStatus()
				);
	}

}
