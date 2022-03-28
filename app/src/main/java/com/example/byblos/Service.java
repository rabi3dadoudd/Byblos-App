package com.example.byblos;

public class Service {
    private String _name;
    public Service(String name){
        _name = name;
    }
    public Service(){
    }
    public String getName(){
        return _name;
    }
    public void setName(String name){
        _name = name;
    }
}
