package com.vendingmachine;

public enum Products {
    COLA (1.00),
    CHIPS (.50),
    CANDY (.65);

    final double PRICE;

    Products(double PRICE) {
        this.PRICE = PRICE;
    }

    public static double PRICE(Products product) {
                return product.PRICE;
    }
}
