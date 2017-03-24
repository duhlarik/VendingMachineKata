package com.vendingmachine;

public enum Products {
    COLA (1.00),
    CHIPS (.50),
    CANDY (.65);

    private final double PRICE;

    Products(double PRICE) {
        this.PRICE = PRICE;
    }

    public static double PRICE(Products product) {
            if (product == Products.valueOf(String.valueOf(product))) {
                return product.PRICE;
        }
        return -1.0;
    }
}
