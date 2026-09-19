package com.ecomeerce.validation.infrastructure.adapter.out.external.item;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.ecomeerce.validation.application.port.out.ItemProviderPort;
import com.ecomeerce.validation.domain.model.Item;

@Component
public class ItemExternalAdapter implements ItemProviderPort {
	
	private final RestClient restClient;
	private final String itemsApiUrl;
	
	public ItemExternalAdapter(
			RestClient restClient,
			@Value("${external.api.items-url}") String itemsApiUrl) {
		this.restClient = restClient;
		this.itemsApiUrl = itemsApiUrl;
	}
	
	@Override
	public List<Item> findAll(){
		List<ItemExternalResponse> response =restClient
				.get()
				.uri(itemsApiUrl)
				.retrieve()
				.body(new ParameterizedTypeReference<>() {
			});
		if(response == null) {
			return List.of();
		}
		
		return response.stream()
				.map(ItemExternalMapper::toDomain)
				.toList();
	}
	
	@Override
	public Optional<Item> findByItemId(String itemId){
		return findAll().stream()
				.filter(item -> itemId.equals(item.itemId()))
				.findFirst();
	}

}
