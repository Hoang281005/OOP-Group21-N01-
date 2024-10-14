package com.example.controller;

import com.example.JDBCconection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    // Function to handle login
    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate login from Login.txt
        if (validateFromFile(username, password)) {
            showAlert("Login successful from file!", Alert.AlertType.INFORMATION);
        } else if (validateFromDatabase(username, password)) {
            showAlert("Login successful from database!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Incorrect username or password!", Alert.AlertType.ERROR);
        }
    }

    // Validate credentials from the Login.txt file
    private boolean validateFromFile(String username, String password) {
        String filePath = "C:\\\\Users\\\\LENOVO\\\\Documents\\\\OOP-Group21-N01-\\\\demo\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\Data\\\\Login.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            showAlert("Error reading file: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        return false;
    }

    // Validate credentials from the MySQL database
    private boolean validateFromDatabase(String username, String password) {
        String query = "SELECT * FROM User WHERE Username = ? AND Password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // If there is a result, the credentials are valid
        } catch (SQLException e) {
            showAlert("Database error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        return false;
    }

    // Helper method to show alert messages
    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}
