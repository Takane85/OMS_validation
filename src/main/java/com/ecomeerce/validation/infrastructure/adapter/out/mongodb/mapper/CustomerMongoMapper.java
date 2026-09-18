package com.ecomeerce.validation.infrastructure.adapter.out.mongodb.mapper;

import com.ecomeerce.validation.domain.model.Customer;
import com.ecomeerce.validation.domain.model.DeliveryAddress;
import com.ecomeerce.validation.infrastructure.adapter.out.mongodb.document.CustomerDocument;

public class CustomerMongoMapper {
	
	private CustomerMongoMapper() {}

	public static CustomerDocument toDocument(Customer customer) {
		CustomerDocument document = new CustomerDocument();
		
		document.setUserId(customer.userId());
		document.setFirstName(customer.firstName());
        document.setPaternalLastName(customer.paternalLastName());
        document.setMaternalLastName(customer.maternalLastName());
        document.setEmail(customer.email());
        
        if(customer.deliveryAddress() != null) {
        	document.setShippingAddress(customer.deliveryAddress().shippingAddress());
        	
        }
        document.setOrders(customer.orders());
        return document;
		
	}
	
	public static Customer toDomain(CustomerDocument document) {
		DeliveryAddress address = new DeliveryAddress(document.getShippingAddress());
		
		return new Customer(
				document.getUserId(),
				document.getFirstName(),
                document.getPaternalLastName(),
                document.getMaternalLastName(),
                document.getEmail(),
                address,
                document.getOrders()
				);
	}
}
