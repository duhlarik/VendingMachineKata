package com.vendingmachine;

public interface VendingMachineObserver {
    void tenderedAmountChanged(double newAmountTendered);

    void productWasDispensed();

    void notEnoughTendered(double price);
}
