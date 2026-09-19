package com.ecomeerce.validation.infrastructure.adapter.in.rest.dto;

import java.util.List;

public record OrderDetailResponse(
		String orderRef,
        String userId,
        String canal,
        String orderStatus,
        boolean marketPlace,
        boolean giftRegistry,
        String storeName,
        List<ItemResponse> items
		) {

}
