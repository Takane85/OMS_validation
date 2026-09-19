package com.ecomeerce.validation.application.usecase;

import com.ecomeerce.validation.application.port.in.CreateCustomerUseCase;
import com.ecomeerce.validation.application.port.in.DeleteCustomerUseCase;
import com.ecomeerce.validation.application.port.in.GetCustomerUseCase;
import com.ecomeerce.validation.application.port.in.UpdateCustomerUseCase;
import com.ecomeerce.validation.application.port.out.CustomerRepositoryPort;
import com.ecomeerce.validation.domain.exception.CustomerAlreadyExistsException;
import com.ecomeerce.validation.domain.exception.CustomerNotFoundException;
import com.ecomeerce.validation.domain.model.Customer;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CustomerService implements
	CreateCustomerUseCase,
	GetCustomerUseCase,
	UpdateCustomerUseCase,
	DeleteCustomerUseCase {
	
	private final CustomerRepositoryPort customerRepositoryPort;
	
	public CustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }
	
	@Override
    public Customer create(Customer customer) {

        if (customerRepositoryPort.existByUserId(customer.userId())) {
            throw new CustomerAlreadyExistsException(customer.userId());
        }

        Customer customerToSave = new Customer(
                customer.userId(),
                customer.firstName(),
                customer.paternalLastName(),
                customer.maternalLastName(),
                customer.email(),
                customer.deliveryAddress(),
                List.of()
        );

        return customerRepositoryPort.save(customerToSave);
    }
	
	@Override
    public Customer getByUserId(String userId) {
        return customerRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new CustomerNotFoundException(userId));
    }

    @Override
    public Customer update(String userId, Customer customer) {

        Customer existingCustomer = getByUserId(userId);

        Customer customerToUpdate = new Customer(
                userId,
                customer.firstName(),
                customer.paternalLastName(),
                customer.maternalLastName(),
                customer.email(),
                customer.deliveryAddress(),
                existingCustomer.orders()
        );

        return customerRepositoryPort.save(customerToUpdate);
    }
    
    @Override
    public void delete(String userId) {

        if (!customerRepositoryPort.existByUserId(userId)) {
            throw new CustomerNotFoundException(userId);
        }

        customerRepositoryPort.deleteByUserId(userId);
    }

}
