package com.vendingmachine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class VendingMachine {

    private List<Double> tendered = new ArrayList<>();
    private List<VendingMachineObserver> observers = new ArrayList<>();
    private ProductInventoryManager inventory;
    private CoinReturn coinInventory;

    VendingMachine(ProductInventoryManager productInventoryManager, CoinReturn coinInventory) {
        inventory = productInventoryManager;
        this.coinInventory = coinInventory;
    }

    void addObserver(VendingMachineObserver observer) {
        observers.add(observer);
    }

    void checkForExactChange() {
        if (coinInventory.outOfChange()) { // TODO: This should probably connect up with the coin return concept.
            observers.forEach(VendingMachineObserver::outOfChange);
        }
    }

    double insertCoin(InsertedCoin insertedCoin) {
        double value = insertedCoin.getCoinValue(insertedCoin);
        if (value == -1.0) {
            return 0.00;
        } else {
            tendered.add(value);
            observers.forEach(observer -> observer.tenderedAmountChanged(getAmountTendered())); // TODO: no unit-level coverage?
            return value;
        }
    }

    public double dispenseProduct(Product product) {
        if (productIsDispensable(product)) {
            observers.forEach(observer -> observer.productWasDispensed(product, -1));
            return returnChange(Product.PRICE(product));
        }
        return 0;
    }

    private boolean productIsDispensable(Product product) {
        double amountTendered = getAmountTendered();
        double price = Product.PRICE(product);
        if (product != Product.NONE && inventory.getInventory(product) == 0) {
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

    private boolean amountTenderedIsEnough(Double amountTendered, Double price) {
        return amountTendered >= price;
    }

    void done() {
        tendered.clear();
        observers.forEach(observer -> observer.tenderedAmountChanged(0));
    }

    private double returnChange(double price) {
        double amountTendered = getAmountTendered();
        BigDecimal change = BigDecimal.valueOf(amountTendered).subtract(BigDecimal.valueOf(price));
        if (price == 0) {
            observers.forEach(observer -> observer.tenderedAmountChanged(0));
        }
        tendered.clear();
        return change.doubleValue(); // TODO: consider other ways of exposing the "coin return" concept
    }
}