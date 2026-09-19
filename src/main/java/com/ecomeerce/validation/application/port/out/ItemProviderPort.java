package com.ecomeerce.validation.application.port.out;

import java.util.List;
import java.util.Optional;
import com.ecomeerce.validation.domain.model.Item;

public interface ItemProviderPort {
	
	List<Item> findAll();
	Optional<Item> findByItemId(String itemId);

}
