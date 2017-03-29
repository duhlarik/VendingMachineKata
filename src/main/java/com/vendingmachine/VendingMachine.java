package com.vendingmachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

class VendingMachine {

    private List<Double> tendered = new ArrayList<>();
    private List<Observer> vm = new ArrayList<>();
    //    private Observable productDispensed = new VendingMachine();
    boolean productIsDispensed = false;
    private List<VendingMachineObserver> observers = new ArrayList<>();


    List<Double> getTendered() {
        return tendered;
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

    Double dispenseProduct(Products product) {
        double amountTendered = amountTendered();
        Double price = Products.PRICE(product);
        Double changeDue = 0.00;
        if (tenderedIsEnough(amountTendered, price)) {
            changeDue = amountTendered - price;
            productIsDispensed = true;
            observers.forEach(observer -> observer.productWasDispensed());
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

    void addObserver(VendingMachineObserver observer){
        observers.add(observer);
    }
}