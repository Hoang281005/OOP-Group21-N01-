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
    private ObservableList<String> productList = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize table columns
        colOrderID.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
        colProductList.setCellValueFactory(cellData -> cellData.getValue().productListProperty());
        colTotalPrice.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        // Load orders and products from the database
        loadOrders();
        loadProducts();
    }

    private void loadOrders() {
        orderList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM `Orders`";
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

    private void loadProducts() {
        productList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT productName FROM Product";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productList.add(rs.getString("productName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        productComboBox.setItems(productList);
    }

    @FXML
    public void handleAddOrder() {
        String product = productComboBox.getValue();
        String quantityText = quantityField.getText();

        if (product == null || quantityText.isEmpty()) {
            showAlert("Input Error", "Please fill all fields.", Alert.AlertType.ERROR);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                showAlert("Input Error", "Quantity must be greater than zero.", Alert.AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Quantity must be a number.", Alert.AlertType.ERROR);
            return;
        }

        double totalPrice = getProductPrice(product) * quantity;

        // Get the current date
        java.sql.Date orderDate = new java.sql.Date(System.currentTimeMillis());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Orders (productList, totalPrice, orderDate) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, product + " x " + quantity);
            stmt.setDouble(2, totalPrice);
            stmt.setDate(3, orderDate); // Set the orderDate
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Order added successfully!", Alert.AlertType.INFORMATION);
                loadOrders();  // Refresh the table
            } else {
                showAlert("Error", "Failed to add order.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to add order: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void handleEditOrder() {
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("No order selected", "Please select an order to edit.", Alert.AlertType.WARNING);
            return;
        }

        String product = productComboBox.getValue();
        String quantityText = quantityField.getText();

        if (product == null || quantityText.isEmpty()) {
            showAlert("Input Error", "Please fill all fields.", Alert.AlertType.ERROR);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                showAlert("Input Error", "Quantity must be greater than zero.", Alert.AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Quantity must be a number.", Alert.AlertType.ERROR);
            return;
        }

        double totalPrice = getProductPrice(product) * quantity;

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Orders SET productList = ?, totalPrice = ? WHERE orderID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, product + " x " + quantity);
            stmt.setDouble(2, totalPrice);
            stmt.setInt(3, selectedOrder.getOrderID());
            stmt.executeUpdate();
            loadOrders();  // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteOrder() {
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("No order selected", "Please select an order to delete.", Alert.AlertType.WARNING);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Orders WHERE orderID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, selectedOrder.getOrderID());
            stmt.executeUpdate();
            loadOrders();  // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double getProductPrice(String product) {
        double price = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT price FROM Product WHERE productName = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, product);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                price = rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    // Helper method to show alert messages
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
