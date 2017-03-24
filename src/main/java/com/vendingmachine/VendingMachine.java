package com.vendingmachine;

import java.util.ArrayList;
import java.util.List;

class VendingMachine {

    private Double sumOfValues = 0.0;
    private List<Double> valuesOfCoins = new ArrayList<>();
    private String currentAmount = "$0.00";
    private String message = "Insert Coins";
    private double change = 0.0;

    public VendingMachine(Double sumOfValues, List<Double> valuesOfCoins) {
        this.sumOfValues = sumOfValues;
        this.valuesOfCoins = valuesOfCoins;
    }

    public VendingMachine() {
    }

    Double findValue(InsertedCoin insertedCoin) {
        Double value = insertedCoin.getCoinValue(insertedCoin);
        if (value != -1.0) {
            valuesOfCoins.add(value);
            return value;
        } else {
            return 0.0;
        }
    }

    double sumOfInsertedCoins() {
        valuesOfCoins.forEach((Double values) -> {
            sumOfValues += values;
            currentAmount = String.valueOf(sumOfValues);
        });
        return sumOfValues;
    }

    String isItEnough(Products product) {
        if (sumOfValues >= Products.PRICE(product)) {
            thankYouAndChange(product);
        } else message = "Insert Coins";
        return message;
    }

    String thankYouAndChange(Products product) {
        message = "Thank you";
        sumOfValues -= Products.PRICE(product);
        change = sumOfValues;
        currentAmount = String.valueOf("Change due: " + change);
        return currentAmount;
    }
}