package com.vendingmachine;

import java.util.HashMap;
import java.util.Map;

public class ProductInventoryManager implements VendingMachineObserver {

    private Map<Product, Integer> inventory = new HashMap<Product, Integer>() {
        {
            put(Product.CHIPS, 0);
            put(Product.CANDY, 0);
            put(Product.COLA, 0);
        }
    };

    @Override
    public void tenderedAmountChanged(double newAmountTendered) {
    }

    @Override
    public void productWasDispensed(Product product, int inventoryChange) {
        if (getInventory(product) != 0) {
            manageInventory(product, inventoryChange);
        }
    }

    @Override
    public void notEnoughTendered(double price) {
    }

    @Override
    public void soldOut(double amountTendered) {
    }

    @Override
    public void outOfChange() {
    }

    void manageInventory(Product product, Integer inventoryChange) {
        int currentInventory = getInventory(product);
        Integer newInventory = currentInventory + inventoryChange;
        updateInventory(product, newInventory);
    }

    int getInventory(Product product) {
        return inventory.get(product);
    }

    void updateInventory(Product product, Integer newInventory) {
        inventory.replace(product, newInventory);
    }
}