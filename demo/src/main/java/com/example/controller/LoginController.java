package com.example.controller;

import com.example.JDBCconection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    // Path to the Login.txt file
    private static final String FILE_PATH = "C:\\\\\\\\Users\\\\\\\\LENOVO\\\\\\\\Documents\\\\\\\\OOP-Group21-N01-\\\\\\\\demo\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\com\\\\\\\\example\\\\\\\\Data\\\\\\\\Login.txt";

    // Function to handle login
    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate login from Login.txt
        if (validateFromFile(username, password)) {
            showAlert("Login successful from file!", Alert.AlertType.INFORMATION);
            openMainWindow();  // Open main window after successful file login
        } 
        // Validate login from database
        else if (validateFromDatabase(username, password)) {
            showAlert("Login successful from database!", Alert.AlertType.INFORMATION);
            openMainWindow();  // Open main window after successful database login
        } else {
            showAlert("Incorrect username or password!", Alert.AlertType.ERROR);
        }
    }

    // Validate credentials from the Login.txt file
    private boolean validateFromFile(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length >= 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
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

    // Function to open the main dashboard after login
    private void openMainWindow() {
        try {
            // Load the main dashboard after successful login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/MainWindow.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Sales Management System");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the login window
            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            showAlert("Error opening the main window: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
