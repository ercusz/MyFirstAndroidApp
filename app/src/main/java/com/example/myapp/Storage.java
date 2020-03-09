package com.example.myapp;

public class Storage {
    private int id;
    private String username;
    private String title;
    private String description;
    private String st_username;
    private String st_password;
    private String date;

    public Storage() {
    }

    public Storage(int id, String username, String title, String description, String st_username, String st_password, String date) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.description = description;
        this.st_username = st_username;
        this.st_password = st_password;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSt_username() {
        return st_username;
    }

    public void setSt_username(String st_username) {
        this.st_username = st_username;
    }

    public String getSt_password() {
        return st_password;
    }

    public void setSt_password(String st_password) {
        this.st_password = st_password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
