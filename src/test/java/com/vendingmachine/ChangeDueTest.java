package com.vendingmachine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChangeDueTest {
    private static final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);
    private static final InsertedCoin DIME = new InsertedCoin(2.268, 17.91);

    private VendingMachine vendingMachine;
    private ProductInventoryManager productInventoryManager;
    private CoinsForChangeDueInventory changeCoins;
    private Display display;

    // TODO: maybe these tests could pay into the broader concept of a "coin return"?

    @Before
    public void setUp() {
        productInventoryManager = new ProductInventoryManager();
        changeCoins = new CoinsForChangeDueInventory();
        display = new Display();
        vendingMachine = new VendingMachine(productInventoryManager, changeCoins);
        vendingMachine.addObserver(display);
        vendingMachine.addObserver(productInventoryManager);
    }

    @After
    public void tearDown() {
        productInventoryManager.updateInventory(Product.CANDY, 0);
        productInventoryManager.updateInventory(Product.CHIPS, 0);
        productInventoryManager.updateInventory(Product.COLA, 0);
    }

    @Test
    public void changeDueWillReturnANickelWhenChangeDueIsFiveCents() {
        // ARRANGE
        productInventoryManager.manageInventory(Product.CANDY, 2);
        changeCoins.updateCoinInventory(CoinsForChangeDue.NICKEL, 2);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(DIME);
        vendingMachine.insertCoin(DIME);

        // ACT
        double change = vendingMachine.dispenseProduct(Product.CANDY);

        // ASSERT
        assertEquals("NICKEL", changeCoins.changeCoin(change));
    }

    @Test
    public void changeDueWillReturnADimeWhenChangeDueIsTenCents() {
        // ARRANGE
        productInventoryManager.manageInventory(Product.CANDY, 2);
        changeCoins.updateCoinInventory(CoinsForChangeDue.DIME, 2);

        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ACT
        double change = vendingMachine.dispenseProduct(Product.CANDY);

        // ASSERT
        assertEquals("DIME", changeCoins.changeCoin(change));
    }
}