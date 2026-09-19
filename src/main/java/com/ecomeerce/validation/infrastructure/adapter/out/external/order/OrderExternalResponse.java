package com.ecomeerce.validation.infrastructure.adapter.out.external.order;

import java.util.List;

public record OrderExternalResponse( 
		String orderRef,
        String userId,
        String canal,
        String orderStatus,
        boolean marketPlace,
        boolean giftRegistry,
        List<String> items,
        String storeName,
        String id
        ) {

}
