package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class OrderList {
    private List<Order> orders;

    public OrderList() {
        orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
