package com.vendingmachine;

public class ProductInventoryManager implements VendingMachineObserver {

    @Override
    public void tenderedAmountChanged(double newAmountTendered) {
    }

    @Override
    public void productWasDispensed(Product product, int inventoryChange) {
        if (ProductInventory.getInventory(product) != 0) {
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
        int currentInventory = ProductInventory.getInventory(product);
        Integer newInventory = currentInventory + inventoryChange;
        ProductInventory.updateInventory(product, newInventory);
    }
}