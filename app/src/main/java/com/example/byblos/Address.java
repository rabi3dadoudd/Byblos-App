package com.example.byblos;

public class Address {
    private String _address;
    public Address(String address){
        _address = address;
    }
    public Address(){
    }
    public String getAddress(){
        return _address;
    }
    public void setAddress(String address){
        _address = address;
    }
}
