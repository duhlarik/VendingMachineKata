package com.vendingmachine;

import java.util.ArrayList;
import java.util.List;

class VendingMachine {

    private List<Double> tendered = new ArrayList<>();
    private List<VendingMachineObserver> observers = new ArrayList<>();

    private InventoryManager inventoryManager = new InventoryManager();
    Display display = new Display();

    List<Double> getTendered() {
        return tendered;
    }

    void addObserver(VendingMachineObserver observer) {
        observers.add(observer);
    }

    Double insertCoin(InsertedCoin insertedCoin) {
        Double value = insertedCoin.getCoinValue(insertedCoin);
        if (value != -1.0) {
            tendered.add(value);
            observers.forEach(observer -> observer.tenderedAmountChanged(getAmountTendered()));
            return value;
        } else return 0.0;
    }

    void dispenseProduct(Products product) {
        double amountTendered = getAmountTendered();
        double price = Products.PRICE(product);

        if (Inventory.getInventory(product) == 0) {
            observers.forEach(observer -> observer.soldOut(amountTendered));
        } else if (amountTenderedIsEnough(amountTendered, price)) {
            observers.forEach(observer -> observer.productWasDispensed(product, -1));
            returnChange(price);
        } else {
            observers.forEach(observer -> observer.notEnoughTendered(price));
        }
    }

    double getAmountTendered() {
        double amountTendered = 0.0;
        for (double value : tendered) {
            amountTendered += value;
        }
        return amountTendered;
    }

    boolean amountTenderedIsEnough(Double amountTendered, Double price) {
        return amountTendered >= price;
    }

    void done() {
        tendered.clear();
        observers.forEach(observer -> observer.tenderedAmountChanged(0));
    }

    double returnChange(double price) {
        double change = getAmountTendered() - price;
        tendered.clear();
        return change;
    }

    double returnCoins() {
        double amountTendered = getAmountTendered();
        tendered.clear();
        observers.forEach(observer -> observer.tenderedAmountChanged(0));
        return amountTendered;
    }
}