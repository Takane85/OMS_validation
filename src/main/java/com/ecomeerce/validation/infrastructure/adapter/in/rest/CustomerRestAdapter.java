package com.ecomeerce.validation.infrastructure.adapter.in.rest;

import com.ecomeerce.validation.application.port.in.CreateCustomerUseCase;
import com.ecomeerce.validation.application.port.in.DeleteCustomerUseCase;
import com.ecomeerce.validation.application.port.in.GetCustomerUseCase;
import com.ecomeerce.validation.application.port.in.UpdateCustomerUseCase;
import com.ecomeerce.validation.domain.model.Customer;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.dto.CustomerRequest;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.dto.CustomerResponse;
import com.ecomeerce.validation.infrastructure.adapter.in.rest.mapper.CustomerRestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestAdapter {

	private final CreateCustomerUseCase createCustomerUseCase;
	private final GetCustomerUseCase getCustomerUseCase;
	private final UpdateCustomerUseCase updateCustomerUseCase;
	private final DeleteCustomerUseCase deleteCustomerUseCase;

	public CustomerRestAdapter(CreateCustomerUseCase createCustomerUseCase, GetCustomerUseCase getCustomerUseCase,
			UpdateCustomerUseCase updateCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase) {

		this.createCustomerUseCase = createCustomerUseCase;
		this.getCustomerUseCase = getCustomerUseCase;
		this.updateCustomerUseCase = updateCustomerUseCase;
		this.deleteCustomerUseCase = deleteCustomerUseCase;
	}

	@PostMapping
	public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {

		Customer customer = createCustomerUseCase.create(CustomerRestMapper.toDomain(request));

		return ResponseEntity.status(HttpStatus.CREATED).body(CustomerRestMapper.toResponse(customer));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<CustomerResponse> getByUserId(@PathVariable String userId) {

		Customer customer = getCustomerUseCase.getByUserId(userId);

		return ResponseEntity.ok(CustomerRestMapper.toResponse(customer));
	}

	@PutMapping("/{userId}")
	public ResponseEntity<CustomerResponse> update(@PathVariable String userId,
			@Valid @RequestBody CustomerRequest request) {

		Customer customer = updateCustomerUseCase.update(userId, CustomerRestMapper.toDomain(request));

		return ResponseEntity.ok(CustomerRestMapper.toResponse(customer));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> delete(@PathVariable String userId) {

		deleteCustomerUseCase.delete(userId);

		return ResponseEntity.noContent().build();
	}

}
