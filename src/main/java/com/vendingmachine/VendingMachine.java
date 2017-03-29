package com.vendingmachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

class VendingMachine implements Observable {

    private boolean tenderedIsEnough = false;
    private List<Double> tendered = new ArrayList<>();
    private List<Observer> vm = new ArrayList<>();
    //    private Observable productDispensed = new VendingMachine();
    boolean productIsDispensed = false;
    private Display display;

    public VendingMachine(Display display) {
        this.display = display;
    }

    List<Double> getTendered() {
        return tendered;
    }

    @Override
    public void addObserver(Observer o) {
        vm.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        vm.remove(o);
    }

    @Override
    public void notifyObserver() {
//        DisplayObserver displayObserver = new DisplayObserver(productDispensed);
//        if (productIsDispensed) {
//            displayObserver.update();
//        }
    }

    Double insertCoin(InsertedCoin insertedCoin) {
        Double value = insertedCoin.getCoinValue(insertedCoin);
        if (value != -1.0) {
            tendered.add(value);
            display.tenderedAmountChanged(amountTendered());
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
            tendered.clear();
        }
        return changeDue;
    }

    boolean tenderedIsEnough(Double amountTendered, Double price) {
        if (amountTendered >= price) {
            tenderedIsEnough = true;
        }
        return tenderedIsEnough;
    }
}