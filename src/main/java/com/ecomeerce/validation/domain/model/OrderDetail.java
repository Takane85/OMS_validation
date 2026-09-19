package com.ecomeerce.validation.domain.model;

import java.util.List;

public record OrderDetail( 
		String orderRef,
		String userId,
		String canal,
		String orderStatus,
		boolean marketPlace,
		boolean giftRegistry,
		String storeName,
		List<Item> items
		) {

}
