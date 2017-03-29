package com.vendingmachine;

public class InsertedCoin {

    private double mass;
    private double diameter;

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


    double getCoinValue(InsertedCoin insertedCoin) {
        return CoinAppraiser.VALUE(insertedCoin);
    }
}