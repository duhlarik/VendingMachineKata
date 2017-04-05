package com.vendingmachine;

public interface VendingMachineObserver {

    void tenderedAmountChanged(double newAmountTendered);

    void productWasDispensed(Products product, int inventoryChange);

    void notEnoughTendered(double price);

    void soldOut(double amountTendered);
}
