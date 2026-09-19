package com.ecomeerce.validation.infrastructure.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.ecomeerce.validation.application.port.in.CreateCustomerUseCase;
import com.ecomeerce.validation.application.port.in.DeleteCustomerUseCase;
import com.ecomeerce.validation.application.port.in.GetCustomerOrdersUseCase;
import com.ecomeerce.validation.application.port.in.GetCustomerUseCase;
import com.ecomeerce.validation.application.port.in.UpdateCustomerUseCase;
import com.ecomeerce.validation.domain.model.Customer;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.dto.CustomerRequest;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.dto.CustomerResponse;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.dto.OrderResponse;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.mapper.CustomerRestMapper;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.mapper.OrderRestMapper;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Customers",
        description = "Customer management operations"
)
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestAdapter {

	private final CreateCustomerUseCase createCustomerUseCase;
	private final GetCustomerUseCase getCustomerUseCase;
	private final UpdateCustomerUseCase updateCustomerUseCase;
	private final DeleteCustomerUseCase deleteCustomerUseCase;
	private final GetCustomerOrdersUseCase getCustomerOrdersUseCase;

	public CustomerRestAdapter(CreateCustomerUseCase createCustomerUseCase, GetCustomerUseCase getCustomerUseCase,
			UpdateCustomerUseCase updateCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase, GetCustomerOrdersUseCase getCustomerOrdersUseCase) {

		this.createCustomerUseCase = createCustomerUseCase;
		this.getCustomerUseCase = getCustomerUseCase;
		this.updateCustomerUseCase = updateCustomerUseCase;
		this.deleteCustomerUseCase = deleteCustomerUseCase;
		this.getCustomerOrdersUseCase = getCustomerOrdersUseCase;
	}

	@Operation(summary = "Create Customer")
	@PostMapping
	public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {

		Customer customer = createCustomerUseCase.create(CustomerRestMapper.toDomain(request));

		return ResponseEntity.status(HttpStatus.CREATED).body(CustomerRestMapper.toResponse(customer));
	}

	@Operation(
	        summary = "Get customer",
	        description = "Retrieves customer information enriched with associated order references."
	)
	@GetMapping("/{userId}")
	public ResponseEntity<CustomerResponse> getByUserId(@PathVariable String userId) {

		Customer customer = getCustomerUseCase.getByUserId(userId);

		return ResponseEntity.ok(CustomerRestMapper.toResponse(customer));
	}

	@Operation(summary = "Update customer")
	@PutMapping("/{userId}")
	public ResponseEntity<CustomerResponse> update(@PathVariable String userId,
			@Valid @RequestBody CustomerRequest request) {

		Customer customer = updateCustomerUseCase.update(userId, CustomerRestMapper.toDomain(request));

		return ResponseEntity.ok(CustomerRestMapper.toResponse(customer));
	}

	@Operation(summary = "Delete customer")
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> delete(@PathVariable String userId) {

		deleteCustomerUseCase.delete(userId);

		return ResponseEntity.noContent().build();
	}
	
	@Operation(
	        summary = "Get customer orders",
	        description = "Retrieves orders associated with the customer."
	)
	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable String userId){
		
		List<OrderResponse> orders = getCustomerOrdersUseCase
				.getOrdersByUserId(userId)
				.stream()
				.map(OrderRestMapper::toResponse)
				.toList();
		
		return ResponseEntity.ok(orders);
	}

}
