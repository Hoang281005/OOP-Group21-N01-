package com.example.controller;

import com.example.JDBCconection.DatabaseConnection;
import com.example.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class OrderListController {

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order, Double> orderTotalColumn;

    private ObservableList<Order> orderList;

    public void initialize() {
        orderList = FXCollections.observableArrayList(); // Initialize the ObservableList

        // Setting up table columns
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("productList"));
        orderTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Loading the orders into the table
        loadOrders();
    }

    private void loadOrders() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT orderID, orderDate, totalPrice FROM Orders";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderID");
                String orderDate = rs.getString("orderDate");
                double totalPrice = rs.getDouble("totalPrice");

                // Create new Order object
                Order order = new Order(orderId, orderDate, totalPrice);
                orderList.add(order); // Add to the observable list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the items in the table
        orderTable.setItems(orderList);
    }

    public void addOrder(Order order) {
        orderList.add(order);
        orderTable.getItems().add(order);
    }
}
