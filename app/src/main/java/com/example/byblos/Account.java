package com.example.byblos;

public class Account {
    private String _username;
    public Account(String user){
        _username = user;
    }
    public Account(){
    }
    public String getUsername(){
        return _username;
    }
    public void setUsername(String fullname){
        _username = fullname;
    }
}
