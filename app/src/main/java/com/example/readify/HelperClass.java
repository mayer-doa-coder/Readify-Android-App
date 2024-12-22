package com.example.readify;

public class HelperClass {

    String name,email,username,password,userId;

    public HelperClass(){}
    public HelperClass(String userId,String name, String email, String username, String password) {
        this.userId=userId;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
