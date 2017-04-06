package com.vendingmachine;

public interface VendingMachineObserver {

    void tenderedAmountChanged(double newAmountTendered);

    void productWasDispensed(Product product, int inventoryChange);

    void notEnoughTendered(double price);

    void soldOut(double amountTendered);

    void outOfChange();
}
