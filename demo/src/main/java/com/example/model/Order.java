package com.example.model;

import javafx.beans.property.*;

public class Order {

    private IntegerProperty orderID;
    private StringProperty productList;
    private DoubleProperty totalPrice;

    // Constructor with parameters
    public Order(int orderID, String productList, double totalPrice) {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.productList = new SimpleStringProperty(productList);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
    }

    // Getters and setters for orderID, productList, and totalPrice
    public int getOrderID() {
        return orderID.get();
    }

    public void setOrderID(int orderID) {
        this.orderID.set(orderID);
    }

    public IntegerProperty orderIDProperty() {
        return orderID;
    }

    public String getProductList() {
        return productList.get();
    }

    public void setProductList(String productList) {
        this.productList.set(productList);
    }

    public StringProperty productListProperty() {
        return productList;
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }
}
