package com.ecomeerce.validation.infrastructure.adapter.out.mongodb.repository;

import com.ecomeerce.validation.infrastructure.adapter.out.mongodb.document.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataCustomerRepository extends MongoRepository<CustomerDocument, String>{

}
