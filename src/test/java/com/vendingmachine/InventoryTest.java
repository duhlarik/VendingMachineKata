package com.vendingmachine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InventoryTest {

    private static final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);
    private static final InsertedCoin DIME = new InsertedCoin(2.268, 17.91);
    private static final InsertedCoin NICKEL = new InsertedCoin(5.000, 21.21);
    private static final InsertedCoin PENNY = new InsertedCoin(2.500, 19.05);

    private static final double DELTA = 1e-15;
    private VendingMachine vendingMachine;
    private InventoryManager inventoryManager;

    @Before
    public void setUp() {
        inventoryManager = new InventoryManager();
        vendingMachine = new VendingMachine();
        vendingMachine.addObserver(inventoryManager);
        Inventory.updateInventory(Products.CANDY, 0);
        Inventory.updateInventory(Products.CHIPS, 0);
        Inventory.updateInventory(Products.COLA, 0);
    }

    @Test
    public void addsInventoryToProductInventory() {
        // ARRANGE
        inventoryManager.manageInventory(Products.COLA, 10);

        // ASSERT
        assertEquals(10, Inventory.getInventory(Products.COLA));
    }

    @Test
    public void decrementsProductInventoryByOneWhenProductIsDispensed() throws Exception {
        // ARRANGE
        inventoryManager.manageInventory(Products.CHIPS, 5);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ACT
        vendingMachine.dispenseProduct(Products.CHIPS);

        // ASSERT
        assertEquals(4, Inventory.getInventory(Products.CHIPS));
    }

}