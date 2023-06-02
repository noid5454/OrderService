//SE_ASS6_Group65_i6297119_i6314833
//Group 65: Matei Turcan & Thu Vo

package com.example.OrderService.controller;

import com.example.OrderService.Order;
import com.example.OrderService.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/placeOrder")
    public Order placeOrder(@RequestBody Order order){
        return ordersService.placeOrder(order);
    }

    @GetMapping("/allOrders")
    public List<Order> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @DeleteMapping("/delete/{id}")
    public String cancelOrder(@PathVariable Integer orderID){
        return ordersService.cancelOrder(orderID);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable int id) {
        return ordersService.getOrder(id);
    }

}