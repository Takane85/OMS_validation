package com.ecomeerce.validation.application.usecase;

import com.ecomeerce.validation.application.port.out.ItemProviderPort;
import com.ecomeerce.validation.application.port.out.OrderProviderPort;
import com.ecomeerce.validation.domain.model.Item;
import com.ecomeerce.validation.domain.model.Order;
import com.ecomeerce.validation.domain.model.OrderDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderProviderPort orderProviderPort;

    @Mock
    private ItemProviderPort itemProviderPort;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(
                orderProviderPort,
                itemProviderPort
        );
    }

    @Test
    void shouldEnrichOrderUsingExactItemId() {

        Order order = new Order(
                "3010091676",
                "user-1",
                "online",
                "2025-12-06",
                false,
                false,
                List.of("3010091676-1132351437"),
                "L SANTA FE"
        );

        Item item = new Item(
                "3010091676-1132351437",
                "1132351437",
                3,
                "Pantalón Levi´s",
                "Compra en línea"
        );

        when(orderProviderPort.findByUserId("user-1"))
                .thenReturn(List.of(order));

        when(itemProviderPort.findAll())
                .thenReturn(List.of(item));

        List<OrderDetail> result =
                orderService.getByUserId("user-1");

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).items().size());
        assertEquals(
                "Pantalón Levi´s",
                result.get(0).items().get(0).displayName()
        );
    }

    @Test
    void shouldUseSkuFallbackWhenExactItemIdDoesNotExist() {

        Order order = new Order(
                "30100916760987",
                "user-1",
                "online",
                "2025-12-06",
                false,
                false,
                List.of("30100916760987-1132351437"),
                "L SANTA FE"
        );

        Item availableItem = new Item(
                "3010091676-1132351437",
                "1132351437",
                3,
                "Pantalón Levi´s",
                "Compra en línea"
        );

        when(orderProviderPort.findByUserId("user-1"))
                .thenReturn(List.of(order));

        when(itemProviderPort.findAll())
                .thenReturn(List.of(availableItem));

        List<OrderDetail> result =
                orderService.getByUserId("user-1");

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).items().size());

        assertEquals(
                "1132351437",
                result.get(0).items().get(0).skuId()
        );

        assertEquals(
                "Pantalón Levi´s",
                result.get(0).items().get(0).displayName()
        );
    }

    @Test
    void shouldReturnEmptyItemsWhenProductCannotBeResolved() {

        Order order = new Order(
                "632005897",
                "user-1",
                "online",
                "2026-05-15",
                false,
                false,
                List.of("632005897-749826482"),
                "Monterrey Centro"
        );

        when(orderProviderPort.findByUserId("user-1"))
                .thenReturn(List.of(order));

        when(itemProviderPort.findAll())
                .thenReturn(List.of());

        List<OrderDetail> result =
                orderService.getByUserId("user-1");

        assertEquals(1, result.size());
        assertTrue(result.get(0).items().isEmpty());
    }

    @Test
    void shouldSearchOrdersIgnoringCaseAndAccents() {

        Order order = new Order(
                "20251208711700070401",
                "user-2",
                "physical",
                "2025-11-20",
                false,
                false,
                List.of(),
                "Liverpool Galerías Serdán"
        );

        when(orderProviderPort.findAll())
                .thenReturn(List.of(order));

        when(itemProviderPort.findAll())
                .thenReturn(List.of());

        List<OrderDetail> result =
                orderService.search("galerias serdan");

        assertEquals(1, result.size());
        assertEquals(
                "20251208711700070401",
                result.get(0).orderRef()
        );
    }

    @Test
    void shouldSearchOrdersWithMinorSpellingError() {

        Order order = new Order(
                "632005897",
                "user-1",
                "online",
                "2026-05-15",
                false,
                false,
                List.of(),
                "Monterrey Centro"
        );

        when(orderProviderPort.findAll())
                .thenReturn(List.of(order));

        when(itemProviderPort.findAll())
                .thenReturn(List.of());

        List<OrderDetail> result =
                orderService.search("monterey");

        assertEquals(1, result.size());
        assertEquals(
                "Monterrey Centro",
                result.get(0).storeName()
        );
    }
}