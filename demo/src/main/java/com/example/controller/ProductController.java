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

        // Lắng nghe khi có sự thay đổi chọn trong bảng sản phẩm
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleSelectProduct(newSelection);  // Gọi hàm để điền thông tin sản phẩm
            }
        });
    }

    // Hàm load sản phẩm từ database
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

    // Hàm tự động điền thông tin sản phẩm khi chọn sản phẩm từ bảng
    @FXML
    public void handleSelectProduct(Product selectedProduct) {
        if (selectedProduct != null) {
            productCodeField.setText(selectedProduct.getProductCode());
            productNameField.setText(selectedProduct.getProductName());
            priceField.setText(String.valueOf(selectedProduct.getPrice()));

            productCodeField.setEditable(false);  // Khóa không cho phép chỉnh sửa mã sản phẩm khi chỉnh sửa
        }
    }

    // Hàm thêm sản phẩm mới vào database
    @FXML
    public void handleAddProduct() {
        String code = productCodeField.getText();
        String name = productNameField.getText();
        double price = Double.parseDouble(priceField.getText());

        if (code.isEmpty() || name.isEmpty() || priceField.getText().isEmpty()) {
            showAlert("All fields must be filled", "Please fill all the fields before adding a product.", Alert.AlertType.WARNING);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Product (productCode, productName, price) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, code);
            stmt.setString(2, name);
            stmt.setDouble(3, price);
            stmt.executeUpdate();
            loadProducts();  // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hàm chỉnh sửa sản phẩm đã chọn
    @FXML
    public void handleEditProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("No product selected", "Please select a product to edit.", Alert.AlertType.WARNING);
            return;
        }

        String name = productNameField.getText();
        double price = Double.parseDouble(priceField.getText());

        if (name.isEmpty() || priceField.getText().isEmpty()) {
            showAlert("All fields must be filled", "Please fill all fields before editing the product.", Alert.AlertType.WARNING);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Product SET productName = ?, price = ? WHERE productCode = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setString(3, selectedProduct.getProductCode());
            stmt.executeUpdate();
            loadProducts();  // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hàm xóa sản phẩm đã chọn
    @FXML
    public void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("No product selected", "Please select a product to delete.", Alert.AlertType.WARNING);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Product WHERE productCode = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, selectedProduct.getProductCode());
            stmt.executeUpdate();
            loadProducts();  // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to show alert messages
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}






