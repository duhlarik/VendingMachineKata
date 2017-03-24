package com.vendingmachine;

public class InsertedCoin {

    private double mass;
    private double diameter;
    private double coinValue;

    InsertedCoin(double mass, double diameter) {
        this.mass = mass;
        this.diameter = diameter;
    }

    double getMass() {
        return mass;
    }

    double getDiameter() {
        return diameter;
    }

    public void setCoinValue(double coinValue) {
        this.coinValue = coinValue;
    }

    double getCoinValue(InsertedCoin insertedCoin) {
        return coinValue = Coin.VALUE(insertedCoin);
    }
}