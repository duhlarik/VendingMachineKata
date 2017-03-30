package com.vendingmachine;

import java.util.HashMap;
import java.util.Map;

public class Inventory implements VendingMachineObserver {

    private Map<Products, Integer> inventory = new HashMap<Products, Integer>() {
        {
            put(Products.CHIPS, 0);
            put(Products.CANDY, 0);
            put(Products.COLA, 0);
        }
    };

    @Override
    public void tenderedAmountChanged(double newAmountTendered) {
    }

    @Override
    public void productWasDispensed(Products product) {
        decreaseInventory(product);
    }

    @Override
    public void notEnoughTendered(double price) {
    }

    @Override
    public void soldOut(Products product, double amountTendered) {
    }

    void increaseInventory(Products product, Integer inventoryAdded) {
        int currentInventory = getInventory(product);
        Integer newInventory = currentInventory + inventoryAdded;
        inventory.replace(product, newInventory);
    }

    private void decreaseInventory(Products product) {
        int currentInventory = getInventory(product);
        Integer newInventory = currentInventory - 1;
        inventory.replace(product, newInventory);
    }

    int getInventory(Products product) {
        return inventory.get(product);
    }
}
