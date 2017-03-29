package com.vendingmachine;

public interface VendingMachineObserver {

    void tenderedAmountChanged(double newAmountTendered);

    void productWasDispensed(Products product);

    void notEnoughTendered(double price);
}
