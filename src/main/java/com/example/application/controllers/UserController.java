package com.example.application.controllers;

public class UserController {
 
    private static Integer userId;
    private String name;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;

    public UserController() {
    }

    public UserController(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
   
    public static void setUserId(Integer id) {
        userId = id;
    }

    public static Integer getId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
   
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}