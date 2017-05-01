package com.vendingmachine;

public class RunVendingMachine {
    public static void main(String[] args) {
        final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);
        final InsertedCoin DIME = new InsertedCoin(2.268, 17.91);
        final InsertedCoin NICKEL = new InsertedCoin(5.000, 21.21);

        ProductInventoryManager inventoryManager = new ProductInventoryManager();
        CoinReturn coinReturn = new CoinReturn();
        Display display = new Display();
        VendingMachine vendingMachine = new VendingMachine(inventoryManager, coinReturn);

        vendingMachine.addObserver(inventoryManager);
        vendingMachine.addObserver(display);

        coinReturn.updateCoinInventory(CoinsForChangeDue.DIME, 1);
        coinReturn.updateCoinInventory(CoinsForChangeDue.NICKEL, 0);

        inventoryManager.updateInventory(Product.CANDY, 4);
        vendingMachine.checkForExactChange();
        System.out.println(display.getMessage());

        vendingMachine.insertCoin(QUARTER);
        System.out.println(display.getMessage());
        vendingMachine.insertCoin(QUARTER);
        System.out.println(display.getMessage());
        vendingMachine.insertCoin(DIME);
        System.out.println(display.getMessage());
        vendingMachine.insertCoin(NICKEL);
        System.out.println(display.getMessage());

        vendingMachine.dispenseProduct(Product.CANDY);
        System.out.println(display.getMessage());
    }
}
