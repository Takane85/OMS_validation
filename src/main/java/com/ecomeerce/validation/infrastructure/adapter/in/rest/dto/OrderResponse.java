package com.ecomeerce.validation.infrastructure.adapter.in.rest.dto;

import java.util.List;

public record OrderResponse( 
		String orderRef,
		String userId,
		String canal,
		String orderStatus,
		boolean marketPlace,
		boolean giftRegistry,
		List<String> items,
		String storeName
		) {

}
