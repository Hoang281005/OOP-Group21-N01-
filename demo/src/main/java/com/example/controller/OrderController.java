package com.example.controller;

import com.example.JDBCconection.DatabaseConnection;
import com.example.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderController {

    @FXML
    private TextField orderIDField;
    @FXML
    private ComboBox<String> productComboBox;
    @FXML
    private TextField quantityField;
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> colOrderID;
    @FXML
    private TableColumn<Order, String> colProductList;
    @FXML
    private TableColumn<Order, Double> colTotalPrice;

    private ObservableList<Order> orderList = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize table columns
        colOrderID.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
        colProductList.setCellValueFactory(cellData -> cellData.getValue().productListProperty());
        colTotalPrice.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        
        // Load orders from the database
        loadOrders();
    }

    private void loadOrders() {
        orderList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM `Order`";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                String productList = rs.getString("productList");
                double totalPrice = rs.getDouble("totalPrice");
                orderList.add(new Order(orderID, productList, totalPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        orderTable.setItems(orderList);
    }

    @FXML
    public void handleAddOrder() {
        // Implement add order functionality
    }

    @FXML
    public void handleEditOrder() {
        // Implement edit order functionality
    }

    @FXML
    public void handleDeleteOrder() {
        // Implement delete order functionality
    }
}
