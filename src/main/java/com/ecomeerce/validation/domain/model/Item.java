package com.ecomeerce.validation.domain.model;

public record Item (
	String itemId,
	String skuId,
	Integer quantity,
	String displayName,
	String deliveryStatus
		) {
	
}
