package com.example.OrderService.controller;

import java.time.LocalDate;

import com.example.OrderService.Order;
import com.example.OrderService.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/placeOrder")
    public Order placeOrder(@RequestBody Order order){
        return orderService.placeOrder(order);
    }

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @DeleteMapping("/delete/{id}")
    public String cancelOrder(@PathVariable Integer orderID){
        return orderService.cancelOrder(orderID);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable int id) {
        return orderService.getOrder(id);
    }

}