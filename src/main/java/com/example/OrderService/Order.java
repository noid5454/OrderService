//SE_ASS6_Group65_i6297119_i6314833
//Group 65: Matei Turcan & Thu Vo

package com.example.OrderService;

import java.time.LocalDate;

public class Order {
    private int id;
    private int bookID;
    private int quantity;
    private LocalDate orderDate;

    public Order(int id, int bookID, int quantity, LocalDate orderDate) {
        this.id = id;
        this.bookID = bookID;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
