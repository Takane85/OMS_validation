package com.ecomeerce.validation.infrastructure.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecomeerce.validation.application.port.in.GetOrderDetailsUseCase;
import com.ecomeerce.validation.application.port.in.SearchOrdersUseCase;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.dto.OrderDetailResponse;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.mapper.OrderDetailRestMapper;

import java.util.List;

@Tag(
        name = "Orders",
        description = "Order retrieval and flexible search operations"
)
@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestAdapter {

	private final GetOrderDetailsUseCase getOrderDetailsUseCase;
	private final SearchOrdersUseCase searchOrdersUseCase;

	public OrderRestAdapter(
			GetOrderDetailsUseCase getOrderDetailsUseCase,
			SearchOrdersUseCase searchOrdersUseCase) {
		this.getOrderDetailsUseCase = getOrderDetailsUseCase;
		this.searchOrdersUseCase = searchOrdersUseCase;
	}

	@Operation(
	        summary = "Get orders by customer",
	        description = "Retrieves customer orders enriched with product information."
	)
	@GetMapping("/customer/{userId}")
	public ResponseEntity<List<OrderDetailResponse>> getByUserId(@PathVariable String userId) {
		List<OrderDetailResponse> response = getOrderDetailsUseCase.getByUserId(userId).stream()
				.map(OrderDetailRestMapper::toResponse).toList();

		return ResponseEntity.ok(response);
	}
	
	@Operation(
	        summary = "Search orders",
	        description = "Flexible search by order reference, order status or store name. "
	                + "Supports partial text, case-insensitive, accent-insensitive "
	                + "and typo-tolerant searches."
	)
	@GetMapping("/search")
	public ResponseEntity<List<OrderDetailResponse>> search(
	        @RequestParam(name = "q", defaultValue = "") String query) {

	    List<OrderDetailResponse> response = searchOrdersUseCase
	            .search(query)
	            .stream()
	            .map(OrderDetailRestMapper::toResponse)
	            .toList();

	    return ResponseEntity.ok(response);
	}

}
