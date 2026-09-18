package com.ecomeerce.validation.infrastructure.adapter.out.mongodb;

import java.util.Optional;
import com.ecomeerce.validation.application.port.out.CustomerRepositoryPort;
import com.ecomeerce.validation.domain.model.Customer;
import com.ecomeerce.validation.infrastructure.adapter.out.mongodb.document.CustomerDocument;
import com.ecomeerce.validation.infrastructure.adapter.out.mongodb.mapper.CustomerMongoMapper;
import com.ecomeerce.validation.infrastructure.adapter.out.mongodb.repository.SpringDataCustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerMongoAdapter implements CustomerRepositoryPort{
	
	private final SpringDataCustomerRepository repository;
	
	public CustomerMongoAdapter(SpringDataCustomerRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Customer save(Customer customer) {
		
		CustomerDocument document = CustomerMongoMapper.toDocument(customer);
		CustomerDocument saved = repository.save(document);
		
		return CustomerMongoMapper.toDomain(saved);
	}
	
	
	@Override
	public Optional<Customer> findByUserId(String userId){
		return repository.findById(userId).map(CustomerMongoMapper::toDomain);
	}
	
	@Override
	public boolean existByUserId(String userId) {
		return repository.existsById(userId);
	}
	
	@Override
	public void deleteByUserId(String userId) {
	    repository.deleteById(userId);
	 }

}
