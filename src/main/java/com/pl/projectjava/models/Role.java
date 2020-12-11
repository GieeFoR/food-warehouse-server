package com.pl.projectjava.models;

public enum Role {
    Admin("admin"),
    Manager("manager"),
    Employee("employee"),
    Customer("customer");

    private String name;
    Role (String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
