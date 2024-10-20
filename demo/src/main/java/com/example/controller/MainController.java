package com.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    @FXML
    public void openProductView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ProductView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Product Management");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openOrderView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/OrderView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Order Management");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showOrderList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/OrderListView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Order List");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
