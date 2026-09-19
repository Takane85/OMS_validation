package com.ecomeerce.validation.infrastructure.adapter.out.external.item;

public record ItemExternalResponse( 
		String itemId,
		String skuId,
		Integer quantity,
		String displayName,
		String deliveryStatus,
		String id
		) {

}
