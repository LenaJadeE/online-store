package com.pluralsight;

public class Product {

    private final String sku;
    private final String name;
    private final double price;
    private final String department;

    private Product(String sku, String name, double price, String department) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.department = department;
    }

    private String getSku() {
        return sku;
    }
    private String getName() {
        return name;
    }

    double getPrice() {
        return  price;
    }

    private  String getDepartment() {
        return department;
    }
}
