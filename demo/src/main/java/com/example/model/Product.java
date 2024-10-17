package com.example.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    private final SimpleStringProperty productCode;
    private final SimpleStringProperty productName;
    private final SimpleDoubleProperty price;

    public Product(String productCode, String productName, double price) {
        this.productCode = new SimpleStringProperty(productCode);
        this.productName = new SimpleStringProperty(productName);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getProductCode() {
        return productCode.get();
    }

    public String getProductName() {
        return productName.get();
    }

    public double getPrice() {
        return price.get();
    }

    // Property methods for data binding
    public SimpleStringProperty productCodeProperty() {
        return productCode;
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }
}
