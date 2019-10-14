package com.fhnw.dao;

public enum EmailType {
    HAM("ham"),SPAM("spam");

    String name;

    EmailType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
