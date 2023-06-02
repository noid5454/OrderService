package com.example.OrderService;

import com.example.OrderService.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Or;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class OrderServiceApplicationTests {
	public OrderService orderService;
	@Mock
	private RestTemplate restTemplate;
	//private InventoryService inventoryService;


	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		orderService = new OrderService();
		orderService.restTemplate = restTemplate;
		when(restTemplate.getForObject(eq("http://localhost:3002/inventory/quantity/1"), eq(Integer.class)))
				.thenReturn(5);

	}

	@Test
	void getAllOrders() {
		List<Order> orderList = orderService.getAllOrders();
		assertEquals(2, orderList.size());
	}

	@Test
	void getOrder() {
		Order order = orderService.getOrder(1);
		assertNotNull(order);
		assertEquals(3, order.getQuantity());

		Order nonExistingOrder = orderService.getOrder(10);
		assertNull(nonExistingOrder);

	}

	@Test
	void placeOrder() {
		Order newOrder = new Order(3, 3, 2, LocalDate.now());

		Order placedOrder = orderService.placeOrder(newOrder);

		assertEquals(newOrder, placedOrder);
		assertTrue(orderService.orders.contains(newOrder));
	}


	@Test
	void cancelOrder() {
		int orderId = 1;
		String expectedMessage = "Order with id " + orderId + " has been cancelled";
		String message = orderService.cancelOrder(orderId);
		assertEquals(expectedMessage, message);

		int nonExistedId = 3;
		String expectedMessage1 = "Order with id " + nonExistedId + " does not exist in the repository";

		String message1 = orderService.cancelOrder(nonExistedId);

		assertEquals(expectedMessage1, message1);


	}

}
