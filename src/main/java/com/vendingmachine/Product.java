package com.vendingmachine;

public enum Product {
    COLA (1.00),
    CHIPS (.50),
    CANDY (.65);

    final double PRICE;

    Product(double PRICE) {
        this.PRICE = PRICE;
    }

    public static double PRICE(Product product) {
                return product.PRICE;
    }
}