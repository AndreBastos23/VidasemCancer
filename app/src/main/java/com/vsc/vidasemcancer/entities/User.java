package com.vsc.vidasemcancer.entities;

public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Username: " + username + " Password: " + password;
    }
}
