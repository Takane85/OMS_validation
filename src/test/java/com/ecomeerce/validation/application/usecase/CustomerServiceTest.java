package com.ecomeerce.validation.application.usecase;

import com.ecomeerce.validation.application.port.out.CustomerRepositoryPort;
import com.ecomeerce.validation.application.port.out.OrderProviderPort;
import com.ecomeerce.validation.domain.exception.CustomerAlreadyExistsException;
import com.ecomeerce.validation.domain.exception.CustomerNotFoundException;
import com.ecomeerce.validation.domain.model.Customer;
import com.ecomeerce.validation.domain.model.DeliveryAddress;
import com.ecomeerce.validation.domain.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    @Mock
    private OrderProviderPort orderProviderPort;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(
                customerRepositoryPort,
                orderProviderPort
        );
    }

    @Test
    void shouldCreateCustomer() {

        Customer customer = createCustomer();

        when(customerRepositoryPort.existByUserId(customer.userId()))
                .thenReturn(false);

        when(customerRepositoryPort.save(customer))
                .thenReturn(customer);

        Customer result = customerService.create(customer);

        assertNotNull(result);
        assertEquals(customer.userId(), result.userId());
        assertEquals(customer.email(), result.email());

        verify(customerRepositoryPort).save(customer);
    }

    @Test
    void shouldRejectDuplicatedCustomer() {

        Customer customer = createCustomer();

        when(customerRepositoryPort.existByUserId(customer.userId()))
                .thenReturn(true);

        assertThrows(
                CustomerAlreadyExistsException.class,
                () -> customerService.create(customer)
        );

        verify(customerRepositoryPort, never())
                .save(any(Customer.class));
    }

    @Test
    void shouldThrowExceptionWhenCustomerDoesNotExist() {

        when(customerRepositoryPort.findByUserId("unknown-user"))
                .thenReturn(Optional.empty());

        assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.getByUserId("unknown-user")
        );
    }

    @Test
    void shouldEnrichCustomerWithOrderReferences() {

        Customer customer = createCustomer();

        Order order = new Order(
                "3010091676",
                customer.userId(),
                "online",
                "2025-12-06",
                false,
                false,
                List.of("3010091676-1132351437"),
                "L SANTA FE"
        );

        when(customerRepositoryPort.findByUserId(customer.userId()))
                .thenReturn(Optional.of(customer));

        when(orderProviderPort.findByUserId(customer.userId()))
                .thenReturn(List.of(order));

        Customer result =
                customerService.getByUserId(customer.userId());

        assertEquals(1, result.orders().size());
        assertEquals("3010091676", result.orders().get(0));
    }

    private Customer createCustomer() {

        return new Customer(
                "75c97531-abf5-4524-8107-90aa48d08efc",
                "Customer",
                "Orders",
                "Demo",
                "customer.orders@example.com",
                new DeliveryAddress("Ciudad de Mexico"),
                List.of()
        );
    }
}