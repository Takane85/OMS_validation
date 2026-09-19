package com.ecomeerce.validation.domain.model;

import java.util.List;

public record Order ( 
		String orderRef,
        String userId,
        String canal,
        String orderStatus,
        boolean marketPlace,
        boolean giftRegistry,
        List<String> itemIds,
        String storeName
		){

}
