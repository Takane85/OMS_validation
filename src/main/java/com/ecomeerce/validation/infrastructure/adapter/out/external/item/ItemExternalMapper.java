package com.ecomeerce.validation.infrastructure.adapter.out.external.item;

import com.ecomeerce.validation.domain.model.Item;

public final class ItemExternalMapper {
	
	private ItemExternalMapper() {}
	
	public static Item toDomain(ItemExternalResponse response) {
        return new Item(
                response.itemId(),
                response.skuId(),
                response.quantity(),
                response.displayName(),
                response.deliveryStatus()
        );
    }

}
