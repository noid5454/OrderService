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
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Order> orders;

    public OrderService() {
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

    //net private methods are helper methods to get extra details from the inventory microservice to aid in creating an order
    public Integer getBookQuantity(Integer invBookId){
        int quantity = restTemplate.getForObject("http://localhost:3002/inventory/quantity/" + invBookId, Integer.class);
        return quantity;
    }

    public void updateBookQuantity(Integer invBookId, int newQuantity){
        InventoryBook invBook = new InventoryBook();
        invBook.setBookId(invBookId);
        invBook.setQuantity(newQuantity);
        restTemplate.put("http://localhost:3002/inventory/update/" + invBookId, invBook);
    }

    public Order placeOrder(Order newOrder){
        int orderedQuantity = newOrder.getQuantity();
        int availableQuantity = getBookQuantity(newOrder.getBookID());

        if (orderedQuantity < availableQuantity) {
            //if the order is valid and is about to be placed, update the quantity
            int updatedQuantity = availableQuantity - orderedQuantity;
            updateBookQuantity(newOrder.getBookID(), updatedQuantity);
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

//    private void updateOrder(Order order, Book book) {
//        int availableQuantity = book.getQuantity();
//        int orderedQuantity = order.getQuantity();
//
//        if (orderedQuantity > availableQuantity){
//            throw new IllegalArgumentException("Insufficient quantity in stock");
//        }
//        book.setQuantity(availableQuantity - orderedQuantity);
//        bookCatalogService.updateBook(book.getId(), book);
//
//        //try to implement inventory service here
//        inventoryService.setBookQuantity(book.getId(), availableQuantity - orderedQuantity);
//    }


}

