package com.example.demofirebase;

public class Urs {
    private String name;
    private String email;

    public Urs(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Urs() {
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
}
