package com.example.controller;

import com.example.JDBCconection.DatabaseConnection;
import com.example.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductController {

    @FXML
    private TextField productCodeField, productNameField, priceField;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> colProductCode;
    @FXML
    private TableColumn<Product, String> colProductName;
    @FXML
    private TableColumn<Product, Double> colPrice;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize table columns
        colProductCode.setCellValueFactory(cellData -> cellData.getValue().productCodeProperty());
        colProductName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        colPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        // Load products from the database
        loadProducts();
    }

    private void loadProducts() {
        productList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Product";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String code = rs.getString("productCode");
                String name = rs.getString("productName");
                double price = rs.getDouble("price");
                productList.add(new Product(code, name, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        productTable.setItems(productList);
    }

    @FXML
    public void handleAddProduct() {
        String code = productCodeField.getText();
        String name = productNameField.getText();
        double price = Double.parseDouble(priceField.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Product (productCode, productName, price) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, code);
            stmt.setString(2, name);
            stmt.setDouble(3, price);
            stmt.executeUpdate();
            loadProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleEditProduct() {
        // Implement edit functionality
    }

    @FXML
    public void handleDeleteProduct() {
        // Implement delete functionality
    }
}

