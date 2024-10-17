package com.example.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
    private final SimpleIntegerProperty orderID;
    private final SimpleStringProperty productList;
    private final SimpleDoubleProperty totalPrice;

    public Order(int orderID, String productList, double totalPrice) {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.productList = new SimpleStringProperty(productList);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
    }

    public int getOrderID() {
        return orderID.get();
    }

    public String getProductList() {
        return productList.get();
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    // Property methods for data binding
    public SimpleIntegerProperty orderIDProperty() {
        return orderID;
    }

    public SimpleStringProperty productListProperty() {
        return productList;
    }

    public SimpleDoubleProperty totalPriceProperty() {
        return totalPrice;
    }
}
