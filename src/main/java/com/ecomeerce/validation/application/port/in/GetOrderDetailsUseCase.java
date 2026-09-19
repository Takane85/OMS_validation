package com.ecomeerce.validation.application.port.in;

import java.util.List;

import com.ecomeerce.validation.domain.model.OrderDetail;

public interface GetOrderDetailsUseCase {
	
	List<OrderDetail> getByUserId(String userId);

}
