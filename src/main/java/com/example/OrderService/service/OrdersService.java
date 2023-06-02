//SE_ASS6_Group65_i6297119_i6314833
//Group 65: Matei Turcan & Thu Vo

package com.example.OrderService.service;

import com.example.OrderService.InventoryBook;
import com.example.OrderService.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the service class that handles the ordering microservice. This class communicates with the InvetoryService
 * class to handle the actual placement of orders. Again, this contains a list to emulate a database of orders.
 */

@Service
public class OrdersService {

    @Autowired
    public RestTemplate restTemplate;

    public List<Order> orders;

    public OrdersService() {
        this.orders = new ArrayList<>();

        Order order1 = new Order(1,2,3,LocalDate.now());
        Order order2 = new Order(2, 1, 2, LocalDate.now());

        orders.addAll(Arrays.asList(order1, order2));

    }
    public List<Order> getAllOrders(){
        return orders;
    }

    public Order getOrder(int id){
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    //helper method to receive just the quantity
    public Integer getBookQuantity(Integer invBookId){
        int quantity = restTemplate.getForObject("http://localhost:3002/inventory/quantity/" + invBookId, Integer.class);
        System.out.println(quantity);
        return quantity;
    }

    //helper method to update the quantity
    public void updateBookQuantity(Integer invBookId, Integer bookID, int newQuantity) {
        InventoryBook currentBook = restTemplate.getForObject("http://localhost:3002/inventory/" + invBookId, InventoryBook.class);

        if (currentBook != null) {
            InventoryBook updatedBook = new InventoryBook();
            updatedBook.setId(invBookId);
            updatedBook.setBookId(bookID);
            updatedBook.setQuantity(newQuantity);

            restTemplate.put("http://localhost:3002/inventory/update/" + invBookId, updatedBook);
        } else {
            throw new IllegalArgumentException("Inventory book with ID " + invBookId + " not found.");
        }
    }


    public Order placeOrder(Order newOrder){
        Integer bookId = newOrder.getBookID();
        int orderedQuantity = newOrder.getQuantity();
        int availableQuantity = getBookQuantity(bookId);

        System.out.println("Order bookID: " + newOrder.getBookID());

        if (orderedQuantity <= availableQuantity) {
            //if the order is valid and is about to be placed, update the quantity
            int updatedQuantity = availableQuantity - orderedQuantity;
            updateBookQuantity(newOrder.getBookID(), bookId, updatedQuantity);
            newOrder.setOrderDate(LocalDate.now());
            orders.add(newOrder);
        } else{
            throw new IllegalArgumentException("Insufficient quantity in stock");
        }
        return newOrder;
    }

    public String cancelOrder(Integer id){
        for(Order curOrder: orders){
            if(id.equals(curOrder.getId())){
                orders.remove(curOrder);
                return "Order with id "+id+" has been cancelled" ;
            }
        }
        return "Order with id "+id+" does not exist in the repository";
    }

}

