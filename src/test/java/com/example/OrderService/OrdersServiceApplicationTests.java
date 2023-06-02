//SE_ASS6_Group65_i6297119_i6314833
//Group 65: Matei Turcan & Thu Vo

package com.example.OrderService;

import com.example.OrderService.service.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class OrdersServiceApplicationTests {
	public OrdersService ordersService;
	@Mock
	private RestTemplate restTemplate;
	//private InventoryService inventoryService;


	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		ordersService = new OrdersService();
		ordersService.restTemplate = restTemplate;
		when(restTemplate.getForObject(eq("http://localhost:3002/inventory/quantity/1"), eq(Integer.class)))
				.thenReturn(5);

	}

	@Test
	void getAllOrders() {
		List<Order> orderList = ordersService.getAllOrders();
		assertEquals(2, orderList.size());
	}

	@Test
	void getOrder() {
		Order order = ordersService.getOrder(1);
		assertNotNull(order);
		assertEquals(3, order.getQuantity());

		Order nonExistingOrder = ordersService.getOrder(10);
		assertNull(nonExistingOrder);

	}

	@Test
	void placeOrder() {
		Order newOrder = new Order(3, 3, 2, LocalDate.now());

		Order placedOrder = ordersService.placeOrder(newOrder);

		assertEquals(newOrder, placedOrder);
		assertTrue(ordersService.orders.contains(newOrder));
	}


	@Test
	void cancelOrder() {
		int orderId = 1;
		String expectedMessage = "Order with id " + orderId + " has been cancelled";
		String message = ordersService.cancelOrder(orderId);
		assertEquals(expectedMessage, message);

		int nonExistedId = 3;
		String expectedMessage1 = "Order with id " + nonExistedId + " does not exist in the repository";

		String message1 = ordersService.cancelOrder(nonExistedId);

		assertEquals(expectedMessage1, message1);


	}

}
