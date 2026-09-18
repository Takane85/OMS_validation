package com.ecomeerce.validation.domain.model;

import java.util.List;

public record Order ( 
		String orderRef,
        String userId,
        String orderStatus,
        String storeName,
        String salesChannel,
        String estimatedDeliveryDate,
        List<Item> items
		){

}
