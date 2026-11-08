package com.pluralsight;

public class Product {

    private String iD;
    private String productName;
    private double price;
    private String department;

    public Product(String iD, String productName, double price, String department) {
        this.iD = iD;
        this.productName = productName;
        this.price = price;
        this.department = department;
    }

    public String getSku() {
        return iD;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Product{" +
                "sku='" + iD + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", department='" + department + '\'' +
                '}';
    }
}

