package com.ecomeerce.validation.application.usecase;

import com.ecomeerce.validation.application.port.in.GetOrderDetailsUseCase;
import com.ecomeerce.validation.application.port.in.SearchOrdersUseCase;
import com.ecomeerce.validation.application.port.out.ItemProviderPort;
import com.ecomeerce.validation.application.port.out.OrderProviderPort;
import com.ecomeerce.validation.application.util.TextSearchUtils;
import com.ecomeerce.validation.domain.model.Item;
import com.ecomeerce.validation.domain.model.Order;
import com.ecomeerce.validation.domain.model.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService implements GetOrderDetailsUseCase, SearchOrdersUseCase {

	private final OrderProviderPort orderProviderPort;
	private final ItemProviderPort itemProviderPort;

	public OrderService(OrderProviderPort orderProviderPort, ItemProviderPort itemProviderPort) {
		this.orderProviderPort = orderProviderPort;
		this.itemProviderPort = itemProviderPort;
	}

	@Override
	public List<OrderDetail> getByUserId(String userId) {

		List<Order> orders = orderProviderPort.findByUserId(userId);
		List<Item> availableItems = itemProviderPort.findAll();

		Map<String, Item> itemsById = itemProviderPort.findAll().stream()
				.collect(Collectors.toMap(Item::itemId, Function.identity(), (first, second) -> first));

		Map<String, Item> itemsBySku = availableItems.stream()
				.collect(Collectors.toMap(Item::skuId, Function.identity(), (first, second) -> first));

		return orders.stream().map(order -> toOrderDetail(order, itemsById, itemsBySku)).toList();
	}

	@Override
	public List<OrderDetail> search(String query) {

		List<Order> orders = orderProviderPort.findAll();

		List<Item> availableItems = itemProviderPort.findAll();

		Map<String, Item> itemsById = availableItems.stream()
				.collect(Collectors.toMap(Item::itemId, Function.identity(), (first, second) -> first));

		Map<String, Item> itemsBySku = availableItems.stream()
				.collect(Collectors.toMap(Item::skuId, Function.identity(), (first, second) -> first));

		return orders.stream().filter(order -> matches(order, query))
				.map(order -> toOrderDetail(order, itemsById, itemsBySku)).toList();
	}

	private OrderDetail toOrderDetail(Order order, Map<String, Item> itemsById, Map<String, Item> itemsBySku) {

		List<Item> items = order.itemIds().stream().map(itemId -> resolveItem(itemId, itemsById, itemsBySku))
				.filter(item -> item != null).toList();

		return new OrderDetail(order.orderRef(), order.userId(), order.canal(), order.orderStatus(),
				order.marketPlace(), order.giftRegistry(), order.storeName(), items);

	}

	private Item resolveItem(String itemId, Map<String, Item> itemsById, Map<String, Item> itemsBySku) {

		Item exactMatch = itemsById.get(itemId);

		if (exactMatch != null) {
			return exactMatch;
		}

		String skuId = extractSkuId(itemId);

		return itemsBySku.get(skuId);
	}

	private String extractSkuId(String itemId) {

		int separatorIndex = itemId.lastIndexOf('-');

		if (separatorIndex < 0 || separatorIndex == itemId.length() - 1) {
			return itemId;
		}

		return itemId.substring(separatorIndex + 1);
	}

	private boolean matches(Order order, String query) {

		return TextSearchUtils.flexibleMatch(order.orderRef(), query)
				|| TextSearchUtils.flexibleMatch(order.orderStatus(), query)
				|| TextSearchUtils.flexibleMatch(order.storeName(), query);
	}

}
