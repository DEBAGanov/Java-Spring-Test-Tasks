package com.example.numbergenerator;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarNumberVariationsToH2Database {
    public static void main(String[] args) {
        String[] letters = {"А", "Е", "Т", "О", "Р", "Н", "У", "К", "Х", "С", "В", "М"};

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:number", "bv", "bvcxz")) {
            // Создание таблицы для хранения номеров
            String createTableQuery = "CREATE TABLE car_numbers (id INT AUTO_INCREMENT PRIMARY KEY, number VARCHAR(20))";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery);
            createTableStatement.executeUpdate();

            for (String letter1 : letters) {
                for (String letter2 : letters) {
                    for (String letter3 : letters) {
                        for (int digit = 0; digit <= 999; digit++) {
                            String carNumber = letter1 + String.format("%03d", digit) + letter2 + letter3 + " 116RUS";

                            String insertQuery = "INSERT INTO car_numbers (number) VALUES (?)";
                            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                            insertStatement.setString(1, carNumber);
                            insertStatement.executeUpdate();

                        }
                    }
                }
            }
            System.out.println("Все номера успешно добавлены в базу данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


