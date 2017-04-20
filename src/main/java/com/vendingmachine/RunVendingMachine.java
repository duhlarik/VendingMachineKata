package com.vendingmachine;

public class RunVendingMachine {
    public static void main(String[] args) {
        ProductInventoryManager inventoryManager = new ProductInventoryManager();
        CoinReturn coinReturn = new CoinReturn();
        Display display = new Display();
        VendingMachine vendingMachine = new VendingMachine(inventoryManager, coinReturn);

        vendingMachine.addObserver(inventoryManager);
        vendingMachine.addObserver(display);

        coinReturn.updateCoinInventory(CoinsForChangeDue.DIME, 2);
        coinReturn.updateCoinInventory(CoinsForChangeDue.NICKEL, 2);
        vendingMachine.checkForExactChange();
        System.out.print(display.getMessage());
    }
}
