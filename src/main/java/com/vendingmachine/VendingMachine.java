package com.vendingmachine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class VendingMachine {

    private List<Double> tendered = new ArrayList<>();
    private List<VendingMachineObserver> observers = new ArrayList<>();

    List<Double> getTendered() {
        return tendered;
    }

    void addObserver(VendingMachineObserver observer) {
        observers.add(observer);
    }

    double insertCoin(InsertedCoin insertedCoin) {
        double value = insertedCoin.getCoinValue(insertedCoin);
        if (value == -1.0) {
            return 0.00;
        } else {
            tendered.add(value);
            observers.forEach(observer -> observer.tenderedAmountChanged(getAmountTendered()));
            return value;
        }
    }

    void dispenseProduct(Product product) {
        if (productIsDispensable(product)) {
            observers.forEach(observer -> observer.productWasDispensed(product, -1));
            returnChange(Product.PRICE(product));
        }
    }

    private boolean productIsDispensable(Product product) {
        double amountTendered = getAmountTendered();
        double price = Product.PRICE(product);
        if (ProductInventory.getInventory(product) == 0) {
            observers.forEach(observer -> observer.soldOut(amountTendered));
            return false;
        }
        if (!(amountTenderedIsEnough(amountTendered, price))) {
            observers.forEach(observer -> observer.notEnoughTendered(price));
            return false;
        }
        return true;
    }

    double getAmountTendered() {
        double amountTendered = 0.00;
        for (Double tenderedValue : tendered) {
            amountTendered += tenderedValue;
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
        DecimalFormat df = new DecimalFormat("#.00");
        Double change = Double.valueOf(df.format(getAmountTendered() - price));
        tendered.clear();
        return change;
    }

    double returnCoins() {
        double amountTendered = getAmountTendered();
        tendered.clear();
        observers.forEach(observer -> observer.tenderedAmountChanged(0));
        return amountTendered;
    }

    void checkForExactChange() {
        if(CoinsForChangeDueInventory.outOfChange()) {
            observers.forEach(VendingMachineObserver::outOfChange);
        }
    }
}