package com.example.OrderService;

import com.example.OrderService.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;
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
		//inventoryService = new InventoryService(new BookCatalogueService());
		orderService = new OrderService();
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

//		int initialQuantity = inventoryService.getBookQuantity(2);
//		int orderedQuantity = 4;
//		Order newOrder = new Order(3, 2, orderedQuantity, LocalDate.now());
//		Order placedOrder = orderService.placeOrder(newOrder);
//		assertNotNull(placedOrder);
//
//		int expectedQuantity = initialQuantity - orderedQuantity;
//		int updatedQuantity = inventoryService.getBookQuantity(2);
//		assertEquals(expectedQuantity, updatedQuantity);
//		Order newOrder = new Order(3,1,2, LocalDate.now());
//		int availableQuantity = 5;
//		int orderQuantity = newOrder.getQuantity();
//		int remainedQuantity = availableQuantity - orderQuantity;
//
//		when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Integer.class)))
//				.thenReturn(availableQuantity);
//
//		Order placedOrder = orderService.placeOrder(newOrder);
//
//		assertEquals(newOrder, placedOrder);
//		assertEquals(remainedQuantity, orderService.getBookQuantity(newOrder.getBookID()));
		Order newOrder = new Order(3, 1, 2, LocalDate.now());
		int availableQuantity = 5;
		int orderedQuantity = newOrder.getQuantity();
		int remaindedQuantity = availableQuantity - orderedQuantity;

		orderService.orders.clear();
		orderService.orders.add(newOrder);

		Order placedOrder = orderService.placeOrder(newOrder);

		assertEquals(newOrder, placedOrder);
		assertEquals(remaindedQuantity, orderService.getBookQuantity(newOrder.getBookID()));
		assertEquals(1, orderService.getAllOrders().size());
	}


//		Order insufficientOrder = new Order(4, 2,10, LocalDate.now() );
//		when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Integer.class)))
//				.thenReturn(availableQuantity);
//		assertThrows(IllegalArgumentException.class, () -> orderService.placeOrder(insufficientOrder));
		
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
