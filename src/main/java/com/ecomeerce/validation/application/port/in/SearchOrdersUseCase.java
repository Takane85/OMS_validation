package com.ecomeerce.validation.application.port.in;

import java.util.List;

import com.ecomeerce.validation.domain.model.OrderDetail;

public interface SearchOrdersUseCase {
	
	List<OrderDetail> search(String query);

}
