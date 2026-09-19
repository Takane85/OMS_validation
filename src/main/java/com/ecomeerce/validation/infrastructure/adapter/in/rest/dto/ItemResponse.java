package com.ecomeerce.validation.infrastructure.adapter.in.rest.dto;

public record ItemResponse( 
		String itemId,
		String skuId,
		Integer quantity,
		String displayName,
		String deliveryStatus
		) {

}
