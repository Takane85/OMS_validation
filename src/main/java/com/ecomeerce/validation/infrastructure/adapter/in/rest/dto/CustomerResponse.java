package com.ecomeerce.validation.infrastructure.adapter.in.rest.dto;

import java.util.List;

public record CustomerResponse(
        String userId,
        String firstName,
        String paternalLastName,
        String maternalLastName,
        String email,
        String shippingAddress,
        List<String> orders
) {
}
