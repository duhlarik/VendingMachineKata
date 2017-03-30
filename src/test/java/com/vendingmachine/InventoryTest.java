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
    private Inventory inventory;

    @Before
    public void setUp() {
        inventory = new Inventory();
        vendingMachine = new VendingMachine();
        vendingMachine.addObserver(inventory);
    }

    @Test
    public void addsInventoryToProductInventory() {
        // ARRANGE
        inventory.increaseInventory(Products.COLA, 10);

        // ASSERT
        assertEquals(10, inventory.getInventory(Products.COLA));
    }

    @Test
    public void decrementsProductInventoryByOneWhenProductIsDispensed() throws Exception {
        // ARRANGE
        inventory.increaseInventory(Products.CHIPS, 5);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ACT
        vendingMachine.dispenseProduct(Products.CHIPS);

        // ASSERT
        assertEquals(4, inventory.getInventory(Products.CHIPS));

    }

}