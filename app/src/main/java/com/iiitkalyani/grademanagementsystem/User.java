package com.iiitkalyani.grademanagementsystem;

public class User {
    private int id;
    private String name;
    private String email;
    private int batch;
    private int regno;

    public User(int id, int  batch, String name, String email,  int regno) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.batch = batch;
        this.regno = regno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getBatch() {
        return batch;
    }


    public int getRegno() {
        return regno;
    }

}
