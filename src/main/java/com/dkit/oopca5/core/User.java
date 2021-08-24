package com.dkit.oopca5.core;

public class User
{
    private int userId;
    private String email;
    private String password;

    public User(int userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "User ID=" + userId+
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
