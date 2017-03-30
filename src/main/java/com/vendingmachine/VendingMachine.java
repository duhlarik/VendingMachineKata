package com.vendingmachine;

import java.util.ArrayList;
import java.util.List;

class VendingMachine {

    private List<Double> tendered = new ArrayList<>();
    private List<VendingMachineObserver> observers = new ArrayList<>();

    List<Double> getTendered() {
        return tendered;
    }

    void addObserver(VendingMachineObserver observer){
        observers.add(observer);
    }

    Double insertCoin(InsertedCoin insertedCoin) {
        Double value = insertedCoin.getCoinValue(insertedCoin);
        if (value != -1.0) {
            tendered.add(value);
            observers.forEach(observer -> observer.tenderedAmountChanged(amountTendered()));
            return value;
        } else {
            return 0.0;
        }
    }

    double amountTendered() {
        double amountTendered = 0.0;
        for (double value : tendered) {
            amountTendered += value;
        }
        return amountTendered;
    }

    double dispenseProduct(Products product) {
        double amountTendered = amountTendered();
        double price = Products.PRICE(product);
        double changeDue = 0.00;
        boolean productIsDispensed = false;
        if (tenderedIsEnough(amountTendered, price)) {
            changeDue = amountTendered - price;
            productIsDispensed = true;
            observers.forEach(observer -> observer.productWasDispensed(product));
            tendered.clear();
        } else {
            observers.forEach(observer -> observer.notEnoughTendered(price));
        }
        return changeDue;
    }

    boolean tenderedIsEnough(Double amountTendered, Double price) {
        boolean tenderedIsEnough = false;
        if (amountTendered >= price) {
            tenderedIsEnough = true;
        }
        return tenderedIsEnough;
    }

    void done() {
        tendered.clear();
        observers.forEach(observer -> observer.tenderedAmountChanged(0));
    }

    double returnCoins() {
        double amountTendered = amountTendered();
        tendered.clear();
        observers.forEach(observer -> observer.tenderedAmountChanged(0));
        return amountTendered;
    }
}