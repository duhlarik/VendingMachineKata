package com.vendingmachine;

public class InventoryManager implements VendingMachineObserver {

    @Override
    public void tenderedAmountChanged(double newAmountTendered) {
    }

    @Override
    public void productWasDispensed(Products product, int inventoryChange) {
        if (Inventory.getInventory(product) != 0) {
            manageInventory(product, inventoryChange);
        }
    }

    @Override
    public void notEnoughTendered(double price) {
    }

    @Override
    public void soldOut(double amountTendered) {

    }

    void manageInventory(Products product, Integer inventoryChange) {
        int currentInventory = Inventory.getInventory(product);
        Integer newInventory = currentInventory + inventoryChange;
        Inventory.updateInventory(product, newInventory);
    }
}