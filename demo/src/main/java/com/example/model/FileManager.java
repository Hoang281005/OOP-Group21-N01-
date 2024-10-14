package com.example.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

    public static final String FILE_PATH = "C:\\Users\\LENOVO\\Documents\\OOP-Group21-N01-\\demo\\src\\main\\resources\\com\\example\\Data\\Login.txt";  // Path to the login file

    // Method to write user information to Login.txt
    public static void writeUserToFile(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + "," + password);
            writer.newLine();  // Write each user on a new line
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to validate user login from Login.txt
    public static boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                System.out.println("abc");
                System.out.println(credentials);
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true;  // If credentials match, login is successful
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;  // If no match is found, login fails
    }
}
