package com.ecomeerce.validation.infrastructure.adapter.out.external.order;

import com.ecomeerce.validation.application.port.out.OrderProviderPort;
import com.ecomeerce.validation.domain.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;


@Component
public class OrderExternalAdapter implements OrderProviderPort{
	
	private final RestClient restClient;
	private final String ordersApiUrl;

	public OrderExternalAdapter(
            RestClient restClient,
            @Value("${external.api.orders-url}") String ordersApiUrl) {

        this.restClient = restClient;
        this.ordersApiUrl = ordersApiUrl;
    }
	
	@Override
    public List<Order> findAll() {

        List<OrderExternalResponse> response = restClient
                .get()
                .uri(ordersApiUrl)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (response == null) {
            return List.of();
        }

        return response.stream()
                .map(OrderExternalMapper::toDomain)
                .toList();
    }
	
	@Override
    public List<Order> findByUserId(String userId) {

        return findAll().stream()
                .filter(order -> userId.equals(order.userId()))
                .toList();
    }
	
}
