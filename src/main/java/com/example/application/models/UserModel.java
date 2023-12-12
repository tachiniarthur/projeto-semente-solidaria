package com.example.application.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.application.controllers.UserController;

public class UserModel {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:database.sqlite";
        createTable(url);
    }

    public static int getUserIdByUsername(String username) {
        String url = "jdbc:sqlite:database.sqlite";
   
        try (Connection c = DriverManager.getConnection(url)) {
            String query = "SELECT id_user FROM user WHERE username = ?";
            try (PreparedStatement statement = c.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        int userId = result.getInt("id_user");
                        UserController.setUserId(userId);
                        return userId;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
   
        return -1;
    }

    public static void createTable(String url) {
        try (Connection c = DriverManager.getConnection(url)) {
            String createTableSql = """
                    CREATE TABLE IF NOT EXISTS user (
                        id_user INTEGER PRIMARY KEY AUTOINCREMENT,
                        name VARCHAR(250),
                        email VARCHAR(250),
                        username VARCHAR(250),
                        password VARCHAR(250)
                    );
                    """;
            try (PreparedStatement createTableStatement = c.prepareStatement(createTableSql)) {
                createTableStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean insertUser(String name, String email, String username, String password) {
        String url = "jdbc:sqlite:database.sqlite";
   
        try (Connection c = DriverManager.getConnection(url)) {
            String insertSql = "INSERT INTO user (name, email, username, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStatement = c.prepareStatement(insertSql)) {
                insertStatement.setString(1, name);
                insertStatement.setString(2, email);
                insertStatement.setString(3, username);
                insertStatement.setString(4, password);
                int rowsAffected = insertStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
   
        return false;
    }

    public static boolean checkCredentials(String username, String password) {
        String url = "jdbc:sqlite:database.sqlite";
       
        try (Connection c = DriverManager.getConnection(url)) {
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement statement = c.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet result = statement.executeQuery()) {
                    return result.next();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return false;
    }

        public static boolean changePassword(String email, String username, String newPassword) {
            String url = "jdbc:sqlite:database.sqlite";

            try (Connection c = DriverManager.getConnection(url)) {
                String userQuery = "SELECT id_user FROM user WHERE email = ? AND username = ?";
                try (PreparedStatement userStatement = c.prepareStatement(userQuery)) {
                    userStatement.setString(1, email);
                    userStatement.setString(2, username);
                    try (ResultSet userResult = userStatement.executeQuery()) {
                        if (userResult.next()) {
                            int userId = userResult.getInt("id_user");
                            String updateQuery = "UPDATE user SET password = ? WHERE id_user = ?";
                            try (PreparedStatement updateStatement = c.prepareStatement(updateQuery)) {
                                updateStatement.setString(1, newPassword);
                                updateStatement.setInt(2, userId);
                                int rowsUpdated = updateStatement.executeUpdate();
                                return rowsUpdated > 0;
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return false;
    }
}